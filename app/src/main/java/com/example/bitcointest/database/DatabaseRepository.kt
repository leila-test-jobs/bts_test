package com.example.bitcointest.database

import com.example.bitcointest.model.Network
import io.objectbox.Box

object DatabaseRepository {
    private val networkBox: Box<Network> = ObjectBox.boxStore.boxFor(Network::class.java)

    fun putNetwork(list: List<Network>){
//        val all = getAllNetwork()
//        var hasItem = false
//        if (networkBox.all.isEmpty()){
//            networkBox.put(list)
//            return
//        } else {
//            for (item in list){
//                if (all?.contains(it))
//            }
//        }
        networkBox.removeAll()
        networkBox.put(list)
    }

    fun getAllNetwork(): MutableList<Network>? {
        return networkBox.all
    }
}