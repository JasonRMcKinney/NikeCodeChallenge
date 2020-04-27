package com.example.nikecodechallenge.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.nikecodechallenge.BuildConfig
import com.example.nikecodechallenge.UrbanDictionaryApp
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import kotlin.math.pow


interface UrbanDictionaryApi {

    @GET(UrbanDictionaryApp.endPoint)
    @Headers(
        value = ["x-rapidapi-host:mashape-community-urban-dictionary.p.rapidapi.com",
            "x-rapidapi-key:sZQlSpvuInmshMY8wG6PjXKbrUWbp1tnO2WjsnnaNosQNMgvZm"
        ]
    )
    fun getDefinition(@Query("term") input: String): Call<DescriptionResponse>

    companion object {
        fun initRetrofit(): UrbanDictionaryApi {
            return Retrofit.Builder()
                .client(client)
                .baseUrl(UrbanDictionaryApp.baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(UrbanDictionaryApi::class.java)
        }

        val client: OkHttpClient by lazy {
            initClient()
        }

        private fun initClient(): OkHttpClient {
            val client = OkHttpClient.Builder()
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            if (BuildConfig.DEBUG) {
                client.addInterceptor(logger)
            }
            client.addInterceptor {
                var request = it.request()
                request = if (isOnline())
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, max-age=" + 2 * 60
                    ).build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 3 * 60
                    ).build()
                it.proceed(request)
            }
                .cache(cacheConfig())
                .build()
            return client.build()
        }

        private fun cacheConfig(): Cache {
            val cacheSize = 2.0.pow(20) * 5  // 5 Mebibyte
            return Cache(UrbanDictionaryApp.urbanDictionaryApp.cacheDir, cacheSize.toLong())

        }

        private fun isOnline(): Boolean {
            val connectiviyManager: ConnectivityManager = UrbanDictionaryApp.urbanDictionaryApp
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            var isConnected = false

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                val networkCapabilities = connectiviyManager.activeNetwork ?: return false
                val activeNetwork =
                    connectiviyManager.getNetworkCapabilities(networkCapabilities) ?: return false

                isConnected = when {
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } else {
                connectiviyManager.run {
                    connectiviyManager.activeNetworkInfo?.run {
                        isConnected = when (type) {
                            ConnectivityManager.TYPE_WIFI -> true
                            ConnectivityManager.TYPE_MOBILE -> true
                            ConnectivityManager.TYPE_ETHERNET -> true
                            else -> false
                        }
                    }
                }
            }
            return isConnected
        }

    }
}