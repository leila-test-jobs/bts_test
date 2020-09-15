package com.example.bitcointest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bitcointest.database.DatabaseRepository
import com.example.bitcointest.model.CurrencyData
import com.example.bitcointest.model.DescriptionData
import com.example.bitcointest.model.Network
import com.example.bitcointest.network.FetchItems

class MainViewModel: ViewModel() {
    private val onNetwork = MutableLiveData<Network>()
    val networkClickListener: LiveData<Network>
        get() = onNetwork

    fun getFromDatabase(): MutableList<Network>? {
        return DatabaseRepository.getAllNetwork()
    }

    fun saveInDatabase(list: List<Network>){
        DatabaseRepository.putNetwork(list)
    }

    fun getCurrency(): LiveData<CurrencyData>{
        return FetchItems.getCurrencies()
    }

    fun getNetworkList(): LiveData<List<Network>>{
        return FetchItems.getNetworks()
    }

    fun getDescription(tag: Int): MutableLiveData<List<DescriptionData>> {
        return FetchItems.getDescription(tag)
    }

    fun onItemClick(network: Network){
        onNetwork.value = network
    }

    fun onClickFinished(){
        onNetwork.value = null
    }
}