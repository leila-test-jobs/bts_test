package com.example.bitcointest.network

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.bitcointest.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FetchItems {
    private const val BASE_URL = "https://ramzinex.com/exchange/api/v1.0/exchange/"
    private lateinit var mRetrofit: Retrofit
    private lateinit var mApiService: ApiService
    private val mQuery = HashMap<String, Int>()

    init {
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mApiService = mRetrofit.create(ApiService::class.java)
    }

    fun getCurrencies(): MutableLiveData<CurrencyData> {
        mQuery["currency_id"] = 1
        val call = mApiService.getCurrencies()
        val incomeData= MutableLiveData<CurrencyData>()

        call.enqueue(object: Callback<Currency>{
            override fun onFailure(call: Call<Currency>?, t: Throwable?) {
                Log.e("Currency Fetch", t.toString())
            }

            override fun onResponse(call: Call<Currency>?, response: Response<Currency>?) {
                incomeData.value = response?.body()?.data?.get(0)
            }
        })

        return incomeData
    }

    fun getNetworks(): MutableLiveData<List<Network>> {
        mQuery["currency_id"] = 1
        val call = mApiService.getNetworks(mQuery)
        val incomeData= MutableLiveData<List<Network>>()

        call.enqueue(object: Callback<Data>{
            override fun onFailure(call: Call<Data>?, t: Throwable?) {
                Log.e("Network Fetch", t.toString())
            }

            override fun onResponse(call: Call<Data>?, response: Response<Data>?) {
                incomeData.value = response?.body()?.data
            }

        })

        return incomeData
    }

    fun getDescription(id: Int): MutableLiveData<List<DescriptionData>>{
        mQuery["network_id"] = id
        mQuery["type_id"] = 1
        val call = mApiService.getNetworkDescription(mQuery)
        val incomeData = MutableLiveData<List<DescriptionData>>()

        call.enqueue(object : Callback<Description>{
            override fun onFailure(call: Call<Description>?, t: Throwable?) {
                Log.e("Description Fetch", t.toString())
            }

            override fun onResponse(call: Call<Description>?, response: Response<Description>?) {
                incomeData.value = response?.body()?.data
            }
        })
        return incomeData
    }

    fun checkConnection(): MutableLiveData<Int>{
        val call = mApiService.checkInternet()
        val incomeData = MutableLiveData<Int>()

        call.enqueue(object : Callback<About>{
            override fun onFailure(call: Call<About>?, t: Throwable?) {
                Log.e("About Fetch", t.toString())
                incomeData.value = 0
            }

            override fun onResponse(call: Call<About>?, response: Response<About>?) {
                incomeData.value = response?.code()
            }
        })

        return incomeData
    }
}