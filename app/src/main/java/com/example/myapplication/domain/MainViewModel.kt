package com.example.myapplication.domain

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.utils.Constants
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    var emailU = mutableStateOf("");//для того  то бы можно было изменять переменную и визуально изменяться на экране
    var passwordU = mutableStateOf("");
    fun SigIn(){
        viewModelScope.launch {
            try{
                //Email - через что будем логиниться
                Constants.supabase.auth.signInWith(Email){
                    email = emailU.value
                    password = passwordU.value
                }

                //вызывать навигацию куда-то после авторизации
            }
            catch (e: Exception){
                println("Error")
                println(e.message.toString())
            }
        }

    }
}