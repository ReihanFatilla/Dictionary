package com.reift.dictionary.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reift.dictionary.model.DefinitionsItem
import com.reift.dictionary.model.DictionaryResponseItem
import com.reift.dictionary.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DictionaryViewModel: ViewModel() {

    var wordDetail = MutableLiveData<DictionaryResponseItem>()

    var definitionString = MutableLiveData<String>()
    
    fun getWordDetail(word: String){
        ApiConfig.getApiService().getWordDetail(word).enqueue(
            object : Callback<List<DictionaryResponseItem>> {
                override fun onResponse(
                    call: Call<List<DictionaryResponseItem>>,
                    response: Response<List<DictionaryResponseItem>>
                ) {
                    val word = response.body()?.get(0)
                    if (word != null) {
                        wordDetail.value = word
                        getAllDefinition(word.meanings!![0]?.definitions)
                    }
                }

                override fun onFailure(call: Call<List<DictionaryResponseItem>>, t: Throwable) {
                }

            }
        )
    }

    fun clearPreviousDefinition(){
        definitionString.value = ""
    }

    private fun getAllDefinition(definitions: List<DefinitionsItem?>?) {
        if (definitions != null) {
            for (i in definitions.indices){
                definitionString.value = definitionString.value + "\n\n$i:${definitions[i]?.definition}"
            }
        }
    }

}