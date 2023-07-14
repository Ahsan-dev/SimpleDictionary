package com.example.simpledictionary.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.simpledictionary.R
import com.example.simpledictionary.databinding.FragmentWordDetailsBinding
import com.example.simpledictionary.models.EnglishWords
import com.example.simpledictionary.repository.SimpleDictDBRepository
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
        val word = simpleDictDBRepo.getWordDetail(id?:1)
        setupUi(word)
    }

    fun setupUi(word: EnglishWords){
        binding.tvDetailWord.text = word.word
        binding.tvDetailType.text = word.type
        binding.tvDetailMeaning.text = word.meaning
    }

    companion object {


    }
}