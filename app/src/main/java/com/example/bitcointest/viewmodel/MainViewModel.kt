package com.example.bitcointest.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bitcointest.R
import com.example.bitcointest.database.DatabaseRepository
import com.example.bitcointest.model.CurrencyData
import com.example.bitcointest.model.DescriptionData
import com.example.bitcointest.model.Network
import com.example.bitcointest.network.FetchItems

class MainViewModel: ViewModel() {

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

    fun setVisibility(network: Network): Int{
        return if (network.is_selected){
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }

    fun checkConnection(): LiveData<Int>{
        return FetchItems.checkConnection()
    }

//    fun changeDrawable(network: Network): Int {
////        view.setBackgroundColor(R.drawable.)
//        return if (network.is_selected){
//            R.drawable.btn_drawable
//        } else {
//            R.drawable.not_selected_btn
//        }
//    }
//
//    fun changeTextColor(network: Network): Int {
//        return if (!network.is_selected){
//            R.color.black
//        } else {
//            R.color.colorAccent
//        }
//    }
}