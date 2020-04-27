package com.example.nikecodechallenge.model

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    private var viewModelListener: ((DescriptionResponse?) -> Unit)? = null

    fun getWordDefinition(input: String) {
        onWordDefinitionChanged(input)
    }

    private fun onWordDefinitionChanged(input: String) {

        UrbanDictionaryApi.initRetrofit().getDefinition(input)
            .enqueue(object : Callback<DescriptionResponse> {
                override fun onFailure(call: Call<DescriptionResponse>, t: Throwable) {
                    viewModelListener?.invoke(null)
                }

                override fun onResponse(
                    call: Call<DescriptionResponse>,
                    response: Response<DescriptionResponse>
                ) {
                    viewModelListener?.invoke(response.body())
                }
            })
    }

    fun setListener(viewModelListener: (DescriptionResponse?) -> Unit) {
        this.viewModelListener = viewModelListener
    }

    fun removeListener() {
        viewModelListener = null
    }
}
