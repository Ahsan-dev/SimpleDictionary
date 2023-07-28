package com.example.simpledictionary.ui.word_detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.simpledictionary.databinding.FragmentWordDetailsBinding
import com.example.simpledictionary.repository.SimpleDictDBRepository
import com.example.simpledictionary.ui.main_list.SecondFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [WordDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class WordDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var _binding: FragmentWordDetailsBinding
    @Inject
    lateinit var simpleDictDBRepo: SimpleDictDBRepository
    private val binding get() = _binding
    private val viewModel: WordDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWordDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt(SecondFragment.ARG_ID)
        Log.d("MainActivity","Received Id is: $id")
        viewModel.getWordDetails(id?:1)
        setupUi()
    }

    fun setupUi(){

        viewModel.word.observe(viewLifecycleOwner, Observer {word->
            binding.tvDetailWord.text = word.word
            binding.tvDetailType.text = word.type
            binding.tvDetailMeaning.text = word.meaning
        })
    }

    companion object {


    }
}