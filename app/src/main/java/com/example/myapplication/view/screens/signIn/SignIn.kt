package com.example.myapplication.view.screens.signIn

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SignIn(navHostController: NavHostController){
    Text(
        text = "Авторизация",
        modifier = Modifier.padding(30.dp)
    )
}