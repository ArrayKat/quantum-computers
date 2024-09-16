package com.example.myapplication.domain.utils

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object Constants {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://iajbxcngwlujimjfrtwp.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlhamJ4Y25nd2x1amltamZydHdwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjY1MTQ5OTIsImV4cCI6MjA0MjA5MDk5Mn0.lIlD4lg6uBHmSD2_WSZ0jGrtXdpfqXoaB4ZPdLn-NVM"
    ){
        install(Auth)
        install(Postgrest)
        install(Storage)
    }

}