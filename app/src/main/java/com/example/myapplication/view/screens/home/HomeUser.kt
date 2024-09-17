package com.example.myapplication.view.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.myapplication.domain.utils.Constants
import com.example.myapplication.model.Components
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun HomeUser(navHostController: NavHostController){
    val scrollState = rememberScrollState()
    var componentsList by remember { mutableStateOf<List<Components>>(listOf()) } //получили список компонентов
    LaunchedEffect(Unit) { //делаем при первом запуске
        withContext(Dispatchers.IO) {
            try {
                componentsList = Constants.supabase.from("Components")
                    .select().decodeList<Components>() //выбираем из супабейз данные и запихиваем в лист
                Log.d("C",componentsList.toString())
                componentsList.forEach{it->
                    Log.d("C",it.name) //для удобного поиска ошибки? в logcats
                }
            } catch (e: Exception){
                Log.d("C", e.message.toString())
            }
        }

    }


        LazyColumn (
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary)
        ){

            items(
                componentsList,
                key = {component -> component.id}
            ){
                component->
                val imageState = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current).data(component.image_url)
                        .size(Size.ORIGINAL).build()
                ).state
                if(imageState is AsyncImagePainter.State.Error){
                    Box( modifier = Modifier
                            .fillMaxSize()
                            .height(200.dp))
                    {
                        CircularProgressIndicator()
                    }
                }
                if(imageState is AsyncImagePainter.State.Success){
                    Image(
                        painter = imageState.painter,
                        contentDescription = "",
//                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
                Text(
                    text = component.name,
                    modifier = Modifier.padding(8.dp),
                )
            }
        }



//    Text(
//        text = "Welcome to home",
//        color = Color.White,
//        fontSize = 32.sp,
//        fontWeight = FontWeight.Bold,
//        modifier = Modifier.padding(bottom = 32.dp)
//    )
}