package com.example.simpledictionary.ui.edit_word

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.simpledictionary.databinding.FragmentFirstBinding
import com.example.simpledictionary.models.EnglishWords
import com.example.simpledictionary.repository.SimpleDictDBRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    @Inject
    lateinit var wordsDBRepository: SimpleDictDBRepository
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: EdtWordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
        binding.btnAddword.setOnClickListener {
            validateAndAddWord()
        }

        observeUI()
    }

    private fun observeUI(){
        viewModel.wordAdded.observe(viewLifecycleOwner, Observer {
            if(it) {
                Toast.makeText(requireContext(), "Word added successfully", Toast.LENGTH_LONG)
                    .show()
                binding.edtWord.setText("")
                binding.edtType.setText("")
                binding.edtMeaning.setText("")
            }
        })
    }

    private fun validateAndAddWord(){
        val word = binding.edtWord.text
        val type = binding.edtType.text
        val meaning = binding.edtMeaning.text

        if(word.isNullOrBlank()){
            binding.edtWord.error = "Enter word!!!"
            binding.edtWord.requestFocus()
        }else if(type.isNullOrBlank()){
            binding.edtType.error = "Enter word type!!!"
            binding.edtType.requestFocus()
        }else if(meaning.isNullOrBlank()){
            binding.edtMeaning.error = "Enter word meaning!!!"
            binding.edtMeaning.requestFocus()
        }else{
            val word = EnglishWords(
                id = 0,
                word = word.toString(),
                type = type.toString(),
                meaning = meaning.toString()
            )
            viewModel.addWord(word)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}