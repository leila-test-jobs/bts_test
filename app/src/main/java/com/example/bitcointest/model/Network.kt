package com.example.bitcointest.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Network(
    @Id
    var id: Long = 0,
    val currency_id: Int,
    val has_tag: Int,
    val show_name: String,
    val withdraw_fee: Double,
    @Transient
    var is_selected:Boolean = false
)