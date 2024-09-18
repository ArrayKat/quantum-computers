package com.example.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var id: String = "",
    var id_role: Int = 0
)
