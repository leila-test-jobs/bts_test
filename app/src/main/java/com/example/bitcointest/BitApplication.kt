package com.example.bitcointest

import android.app.Application
import com.example.bitcointest.database.ObjectBox

class BitApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        ObjectBox.init(this)
    }
}