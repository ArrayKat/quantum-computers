package com.example.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Components (
    val id: Int,
    var name: String,
    var description: String,
    val image_url: String?,
    val category: Int,
    var cost: Int,
    var count:Int
)