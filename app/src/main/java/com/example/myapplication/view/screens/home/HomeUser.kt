package com.example.myapplication.view.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.myapplication.domain.MainViewModel
import com.example.myapplication.domain.utils.Constants
import com.example.myapplication.model.Category
import com.example.myapplication.model.CombinedItem
import com.example.myapplication.model.Components
import com.example.myapplication.model.Role
import com.example.myapplication.model.User
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
//id: CombinedItem
fun HomeUser(navHostController: NavHostController){
    var viewModel = MainViewModel()
    var componentsList by remember { mutableStateOf<List<Components>>(listOf()) } //получили список компонентов
    var categoryList by remember { mutableStateOf<List<Category>>(listOf()) }
    var role by remember { mutableStateOf("") }

    LaunchedEffect(Unit) { //делаем при первом запуске
        withContext(Dispatchers.IO) {
            try {
                componentsList = Constants.supabase.from("Components")
                    .select().decodeList<Components>() //выбираем из супабейз данные и запихиваем в лист


                categoryList = Constants.supabase.from("Category")
                    .select().decodeList<Category>()

                val user = Constants.supabase.auth.currentUserOrNull()
                if(user != null){
                    val roles = Constants.supabase.from("Role").select().decodeList<Role>()
                    val idRole = Constants.supabase.from("Users").select {
                        filter {
                            eq("id", user.id)
                        }
                    }.decodeSingle<User>().id_role
                    if(roles.isNotEmpty()) {
                        role = roles.find { /* it => it.id == idRole  */ it.id == idRole }!!.name_role
                    }
                    Log.d("role", role)
                }

                Log.d("C",componentsList.toString())
                componentsList.forEach{it->
                    Log.d("C",it.name) //для удобного поиска ошибки? в logcats
                }
            } catch (e: Exception){
                Log.d("C", e.message.toString())
            }
        }

    }
    val combinedList = componentsList.map { component ->
        val category = categoryList.find { it.id == component.category }
        CombinedItem(
            component = component,
            categoryName = category?.name ?: "Unknown" // Если категория не найдена, используем "Unknown"
        )
    }

    // Определите новый класс для объединенного элемента

Box(modifier = Modifier
    .background(color = MaterialTheme.colorScheme.secondary)
    .fillMaxSize()
    .padding(25.dp)
    .height(80.dp)
    .width(60.dp),
    contentAlignment = Alignment.Center
)
{
    LazyColumn(
        modifier = Modifier
            .padding(0.dp, 10.dp)
    ) {

        items(
            combinedList
        ) { component ->

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp) // Отступы между карточками
                    .clip(RoundedCornerShape(30.dp))

                    .background(Color.White) // Белый фон для карточки


            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp) // Отступ внутри карточки
                        .fillMaxSize()
                ) {

                    Text(
                        softWrap = true,
                        text = component.categoryName,
                        modifier = Modifier
                            .padding(15.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(color = MaterialTheme.colorScheme.primary)
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        overflow = TextOverflow.Visible,// Отображает весь текст без обрезки
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )

                    val imageState = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current).data(component.component!!.image_url)
                            .size(Size.ORIGINAL).build()
                    ).state
                    if (imageState is AsyncImagePainter.State.Error) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(200.dp)
                        )
                        {
                            CircularProgressIndicator()
                        }
                    }
                    if (imageState is AsyncImagePainter.State.Success) {
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
                        softWrap = true,
                        text = component.component.name,
                        modifier = Modifier.padding(8.dp),
                        overflow = TextOverflow.Visible,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        softWrap = true,
                        text = component.component.description,
                        modifier = Modifier.padding(8.dp),
                        overflow = TextOverflow.Visible,
                        color = Color.Gray,
                        maxLines = 2
                    )
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            softWrap = true,
                            text = "Цена: "+ component.component.cost,
                            modifier = Modifier.padding(8.dp),
                            overflow = TextOverflow.Visible,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primary,

                        )
                        Text(
                            softWrap = true,
                            text = "Кол-во: "+ component.component.count,
                            modifier = Modifier.padding(8.dp),
                            overflow = TextOverflow.Visible,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primary,

                        )
                    }
                    if (role == "Администратор") {
                        Button(
                            onClick = {
                                navHostController.navigate("cardComponent/${component.component.id}") {

                                    popUpTo("homeUser") { //заканчиваем жизненный цикл экрана сплэш
                                        inclusive = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .padding(10.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Изменить",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }

                }
            }
        }
    }
}

}