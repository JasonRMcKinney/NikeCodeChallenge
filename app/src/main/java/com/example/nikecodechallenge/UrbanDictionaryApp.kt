package com.example.nikecodechallenge

import android.app.Application
import android.content.Context

class UrbanDictionaryApp : Application() {

    companion object {
        const val baseUrl = "https://mashape-community-urban-dictionary.p.rapidapi.com/"
        const val endPoint = "define"
        lateinit var urbanDictionaryApp: Context
    }

    override fun onCreate() {
        super.onCreate()
        urbanDictionaryApp = applicationContext
    }


}