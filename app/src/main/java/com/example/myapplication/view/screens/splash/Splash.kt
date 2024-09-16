package com.example.myapplication.view.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun Splash(navHostController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = "Нажми на меня",
            modifier = Modifier









//            modifier = Modifier.clickable {
//                navHostController.navigate("signIn"){
//                    popUpTo("splash"){ //заканчиваем жизненный цикл экрана сплэш
//                        inclusive = true
//                    }
//                }
//            }.padding(30.dp)

        )
    }
    LaunchedEffect(true) {
        delay(5000)
        navHostController.navigate("signIn") {
            popUpTo("splash") { //заканчиваем жизненный цикл экрана сплэш
                inclusive = true
            }
        }
    }

}