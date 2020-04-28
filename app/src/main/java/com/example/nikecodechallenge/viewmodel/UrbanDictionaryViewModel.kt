package com.example.nikecodechallenge.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nikecodechallenge.model.DescriptionResponse
import com.example.nikecodechallenge.model.Repository

class UrbanDictionaryViewModel : ViewModel() {

    private val udDescription = MutableLiveData<DescriptionResponse>()
    private val udDescriptionError = MutableLiveData<String>()

    val repository: Repository by lazy {
        Repository()
    }

    fun getDescription(input: String) {
        repository.setListener(::updateObservable)
        repository.getWordDefinition(input)
    }

    @VisibleForTesting
    fun updateObservable(response: DescriptionResponse?) {
        if (response == null)
            udDescriptionError.postValue("There was an Error, Try again later")
        else
            udDescription.postValue(response)
    }

    fun sortDataUp() {
        udDescription.value?.list?.sortedWith(compareBy {
            it.thumbs_up
        })?.reversed()?.apply {
            udDescription.postValue(DescriptionResponse(this))
        }
    }

    fun sortDataDown() {
        udDescription.value?.list?.sortedWith(compareBy {
            it.thumbs_down
        })?.reversed()?.apply {
            udDescription.postValue(DescriptionResponse(this))
        }
    }

    fun getUdDescription(): LiveData<DescriptionResponse> = udDescription
    fun getUdDescriptionError(): LiveData<String> = udDescriptionError

    override fun onCleared() {
        super.onCleared()
        repository.removeListener()
    }

}
