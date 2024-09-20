package com.example.myapplication.domain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.model.CombinedItem
import com.example.myapplication.model.SelectedComponent
import com.example.myapplication.view.screens.cardsComponents.CardComponent
import com.example.myapplication.view.screens.home.HomeUser
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
        composable("homeUser"){
            HomeUser(controller)
        }
        composable("cardComponent" + "/{idStr}"){
            backStackEntry ->
            val idStr = backStackEntry.arguments?.getString("idStr") ?: ""
            val id: Int = idStr.toInt()
            CardComponent(controller, id)
        }
    }

}