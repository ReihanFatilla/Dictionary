package com.reift.dictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.reift.dictionary.databinding.ActivityMainBinding
import com.reift.dictionary.model.DictionaryResponseItem
import com.reift.dictionary.viewmodel.DictionaryViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: DictionaryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DictionaryViewModel::class.java]

        setupSearchView()
        viewModel.wordDetail.observe(this){
            setUpView(it)
        }
    }

    private fun setUpView(dict: DictionaryResponseItem?) {
        binding.apply {
            if (dict != null) {
                tvWord.text = dict.word
                tvPhonetic.text = dict.phonetic
            }

            viewModel.definitionString.observe(this@MainActivity){
                if(it != null){
                    tvDefinition.text = it
                }
            }
        }
    }

    private fun setupSearchView() {
        binding.svDictionary.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.clearPreviousDefinition()
                    if (query != null) {
                        viewModel.getWordDetail(query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    return true
                }

            }
        )
    }
}