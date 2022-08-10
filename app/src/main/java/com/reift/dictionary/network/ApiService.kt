package com.reift.dictionary.network

import com.reift.dictionary.model.DictionaryResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("en/{word}")
    fun getWordDetail(
        @Path("word")
        word: String
    ): Call<List<DictionaryResponseItem>>
}