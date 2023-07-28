package com.example.simpledictionary.ui.main_list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.simpledictionary.R
import com.example.simpledictionary.adapters.WordsAdapter
import com.example.simpledictionary.databinding.FragmentSecondBinding
import com.example.simpledictionary.repository.SimpleDictDBRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    @Inject
    lateinit var simpleDictDBRepo: SimpleDictDBRepository
    private val viewModel: MainViewModel by viewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var mWordsAdapter: WordsAdapter? = null
    private var mSearchedQuery = ""

    companion object{
        const val ARG_ID = "IdArg"
        const val LAST_SEARCHED_QUERY = "LAST_SEARCHED_QUERY"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.buttonSecond.setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }
        mSearchedQuery = savedInstanceState?.getString(LAST_SEARCHED_QUERY) ?: ""
        viewModel.fetchAllWords(mSearchedQuery)
        renderList()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("DictionaryActivity", "On Save Instance State called")
        outState.putString(LAST_SEARCHED_QUERY, mSearchedQuery)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.d("DictionaryActivity", "On Restored Instance State called")
        mSearchedQuery = savedInstanceState?.getString(LAST_SEARCHED_QUERY) ?: ""
        super.onViewStateRestored(savedInstanceState)
    }

    fun renderList(){
        val cursor = simpleDictDBRepo.getAllWords(mSearchedQuery)
        viewModel.allWordsCursor.observe(viewLifecycleOwner, Observer {
            mWordsAdapter = WordsAdapter(requireContext(),it)
            binding.lvWords.adapter = mWordsAdapter
            binding.lvWords.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, pos, rawId ->
                val bundle = Bundle()
                bundle.putInt(ARG_ID,rawId.toInt())
                findNavController().navigate(R.id.action_SecondFragment_to_wordDetailsFragment,bundle)
            }
        })

    }

    fun searchedList(prefix: String){
        mSearchedQuery = prefix
        Log.d("MainActivity","Search Called on Fragment")
//        val cursor = simpleDictDBRepo.getAllWords(prefix)
//        mWordsAdapter?.changeCursor(cursor)
        viewModel.fetchAllWords(prefix)
        //binding.lvWords.adapter = mWordsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}