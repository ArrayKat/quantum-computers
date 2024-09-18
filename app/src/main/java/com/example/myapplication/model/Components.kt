package com.example.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Components (
    val id: Int,
    val name: String,
    val description: String,
    val image_url: String?,
    val category: Int,
    val cost: Int,
    val count:Int
)