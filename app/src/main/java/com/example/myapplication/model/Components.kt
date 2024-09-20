package com.example.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Components (
    val id: Int = 0,
    var name: String = "",
    var description: String = "",
    val image_url: String? = "",
    val category: Int = 0,
    var cost: Int = 0,
    var count:Int = 0
)