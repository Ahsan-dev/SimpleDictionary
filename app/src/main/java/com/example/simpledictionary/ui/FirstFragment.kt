package com.example.simpledictionary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.simpledictionary.databinding.FragmentFirstBinding
import com.example.simpledictionary.db.DictionaryDBHelper
import com.example.simpledictionary.models.EnglishWords
import com.example.simpledictionary.repository.SimpleDictDBRepository
import dagger.hilt.EntryPoint
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

            val word = EnglishWords(
                id = 0,
                word = "a",
                type = "noun",
                meaning = "First letter of english alphabet"
            )
            wordsDBRepository.addWord(word)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}