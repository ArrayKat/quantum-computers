package com.example.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Role(
    var id: Int = 0,
    var name_role: String = ""
)
