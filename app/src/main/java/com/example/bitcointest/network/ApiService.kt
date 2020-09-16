package com.example.bitcointest.network

import com.example.bitcointest.model.About
import com.example.bitcointest.model.Currency
import com.example.bitcointest.model.Data
import com.example.bitcointest.model.Description
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {
    @GET("currencies")
    fun getCurrencies(): Call<Currency>

    @GET("networks")
    fun getNetworks(@QueryMap map: Map<String, Int>): Call<Data>

    @GET("tips")
    fun getNetworkDescription(@QueryMap map: Map<String, Int>): Call<Description>

    @GET("about")
    fun checkInternet(): Call<About>
}