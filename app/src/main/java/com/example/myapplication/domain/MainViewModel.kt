package com.example.myapplication.domain


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.myapplication.domain.utils.Constants
import com.example.myapplication.model.Components
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.SupabaseClient

import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import kotlinx.serialization.Serializable

@Serializable
class MainViewModel:ViewModel() {
//    var emailU = mutableStateOf("");//для того  то бы можно было изменять переменную и визуально изменяться на экране
//    var passwordU = mutableStateOf("");

    fun sigIn(emailU: String, passwordU: String, navHostController: NavHostController){

        viewModelScope.launch {
            try{
                //Email - через что будем логиниться
                val user = Constants.supabase.auth.signInWith(Email){
                    email = emailU
                    password = passwordU
                }
                //Constants.supabase.auth.from.select
                Log.d("sign in", user.toString())
                Log.d("sign in", Constants.supabase.auth.currentUserOrNull()!!.id)
                Log.d("sign in", "Success")

                navHostController.navigate("homeUser") {
                    popUpTo("sigIn") { //заканчиваем жизненный цикл экрана сплэш
                        inclusive = true
                    }
                }
            }
            catch (e: Exception){
                Log.d("sign in Error", e.message.toString())
            }
        }
    }
    fun changeCard(navHostController: NavHostController, idNew: Int, nameNew:String, descrNew:String, costNew:Int, countNew:Int){
        viewModelScope.launch {
            try {
                /*val result = Constants.supabase.from("Components").update(
                    mapOf(
                        "name" to  nameNew,
                        "description" to descrNew,
                        "cost" to costNew,
                        "count" to countNew
                    )){
                        filter {
                            eq("id", idNew)
                        }}*/
                val result = Constants.supabase.from("Components").update(
                    {
                        set("name", nameNew)
                        set("description", descrNew)
                        set("cost", costNew)
                        set("count", countNew)

                    }){
                    filter {
                        eq("id", idNew)
                    }}


                Log.e("update", result.toString())
                Log.e("update", "Success")

                navHostController.navigate("homeUser") {
                    popUpTo("cardComponent") { //заканчиваем жизненный цикл экрана сплэш
                        inclusive = true
                    }
                }
            } catch (e: Exception) {
                Log.e("update Error", e.message.toString())
            }
        }
    }
}