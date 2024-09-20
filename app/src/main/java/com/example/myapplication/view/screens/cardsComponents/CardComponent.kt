package com.example.myapplication.view.screens.cardsComponents

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun CardComponent(navHostController: NavHostController, nameComponent:Int){
    var MainVM = MainViewModel()
    var componentList by remember { mutableStateOf<List<Components>>(listOf()) }
    var categoryList by remember { mutableStateOf<List<Category>>(listOf()) }
    var component by remember { mutableStateOf<Components?>(null) }
    LaunchedEffect(Unit) { //делаем при первом запуске
        withContext(Dispatchers.IO) {
            try {
                component = Constants.supabase.from("Components")
                    .select{
                        filter {
                            eq("id", nameComponent)
                        }
                    }.decodeSingle<Components>()
                categoryList = Constants.supabase.from("Category")
                    .select().decodeList<Category>()
                componentList = Constants.supabase.from("Components")
                    .select().decodeList<Components>()
                Log.d("C", "Sucsess")
            } catch (e: Exception){
                Log.d("C", e.message.toString())
            }
        }

    }

    val scrollState = rememberScrollState()

    Box(modifier = Modifier
        .background(color = MaterialTheme.colorScheme.secondary)
        .fillMaxSize()
        .padding(25.dp)
        .height(80.dp)

        .width(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState) // Добавляем вертикальную прокрутку
                .fillMaxSize() // Заполняем доступное пространство
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp) // Отступы между карточками
                    .clip(RoundedCornerShape(30.dp))

                    .background(Color.White) // Белый фон для карточки

            ) {
                Column(
                    modifier = Modifier
                        .padding(25.dp) // Отступ внутри карточки
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(30.dp))


                    //картинка
                    val imageState = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(component?.image_url)
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
                    if (component?.id != null && component?.cost != null && component?.name != null && component?.count != null && component?.category != null) {
                        val name = remember { mutableStateOf<String>(component!!.name) }
                        val description =
                            remember { mutableStateOf<String>(component!!.description) }
                        var cost by remember { mutableStateOf<String>(component!!.cost.toString()) }
                        var count by remember { mutableStateOf<String>(component!!.count.toString()) }
                        //Название компплектующей
                        Spacer(modifier = Modifier.height(50.dp))
                        TextField(
                            value = name.value,
                            onValueChange = { name.value = it },
                            label = { Text("Название") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(color = Color.White),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.secondary,
                                unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                                focusedTextColor = Color.White,
                                unfocusedLabelColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedLabelColor = Color.White
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        TextField(
                            value = description.value,
                            onValueChange = { description.value = it },
                            label = { Text("Описание") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(color = Color.White),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.secondary,
                                unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                                focusedTextColor = Color.White,
                                unfocusedLabelColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedLabelColor = Color.White
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        TextField(
                            value = cost,
                            onValueChange = { newValue: String ->
                                // Allow only empty string or strings that consist of digits
                                if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                                    cost = newValue // Update local state
                                    component?.cost =
                                        newValue.toIntOrNull() ?: 0 // Update component cost safely
                                }
                            },
                            label = { Text("Стоимость") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(color = Color.White),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.secondary,
                                unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                                focusedTextColor = Color.White,
                                unfocusedLabelColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedLabelColor = Color.White
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )



                        Spacer(modifier = Modifier.height(16.dp))

                        TextField(
                            value = count,
                            onValueChange = { newValue: String ->
                                // Allow only empty string or strings that consist of digits
                                if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                                    count = newValue // Update local state
                                    component?.count =
                                        newValue.toIntOrNull() ?: 0 // Update component cost safely
                                }
                            },
                            label = { Text("Количество") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(color = Color.White),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.secondary,
                                unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                                focusedTextColor = Color.White,
                                unfocusedLabelColor = Color.White,
                                focusedLabelColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween // равномерное распределение
                        ) {
                            Button(
                                modifier = Modifier
                                    .weight(1f), // Отступ справа для первой кнопки
                                onClick = {
                                    MainVM.changeCard(
                                        navHostController,
                                        component!!.id,
                                        name.value,
                                        description.value,
                                        cost.toInt(),
                                        count.toInt()
                                    )
                                }
                            ) {
                                Text(
                                    text = "Сохранить",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Button(
                                modifier = Modifier
                                    .weight(1f) // Каждая кнопка занимает 50% ширины
                                    .padding(start = 8.dp), // Отступ слева для второй кнопки
                                onClick = {
                                    navHostController.navigate("homeUser") {
                                        popUpTo("cardComponent") { // Заканчиваем жизненный цикл экрана сплэш
                                            inclusive = true
                                        }
                                    }
                                }
                            ) {
                                Text(
                                    text = "Выйти",
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

