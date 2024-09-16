package com.example.myapplication.domain

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.view.screens.home.Home
import com.example.myapplication.view.screens.signIn.SignIn
import com.example.myapplication.view.screens.splash.Splash

@Composable
fun Navigation(){
    val controller = rememberNavController()
    NavHost(
        navController = controller,
        startDestination = "splash"
    ){
        composable("splash"){
            Splash(controller)
        }
        composable("signIn"){
            SignIn(controller)
        }
        composable("home"){
            Home(controller)
        }
    }

}