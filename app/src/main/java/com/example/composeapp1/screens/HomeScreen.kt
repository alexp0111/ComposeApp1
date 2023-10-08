package com.example.composeapp1.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.composeapp1.MyApplication
import com.example.composeapp1.data.MyPhoto
import com.example.composeapp1.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.ObjectInputStream

@Composable
fun HomeScreen(navController: NavController, state: List<Bitmap>) {
    MainContent(navController, state)
}

@Composable
fun MainContent(
    navController: NavController,
    bitmaps: List<Bitmap>,
    mainViewModel: MainViewModel = viewModel(),
) {
    val cards = mainViewModel.photos.collectAsState()
    Surface {
        LazyColumn {
            CoroutineScope(Dispatchers.IO).launch {
                val newList =
                 loadFromMemo(mainViewModel.getApplication<MyApplication>().applicationContext)

                mainViewModel.updatePhotos(newList)
            }
            items(cards.value) { item ->
                ImageRow(item = item)
            }
        }
    }
}

@Composable
fun ImageRow(item: Pair<Bitmap, MyPhoto>) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth(),
        shape = CardDefaults.shape,
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                bitmap = item.first.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
            )
            Spacer(Modifier.size(1.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = item.second.time)
                Text(text = item.second.date)
            }
        }
    }
}

private suspend fun loadFromMemo(context: Context): List<Pair<Bitmap, MyPhoto>> {
    val list = mutableListOf<Pair<Bitmap, MyPhoto>>()
    val rootPath = context.filesDir.path
    val dir = File(rootPath, "photos")
    if (!dir.exists()) dir.mkdir()
    dir.listFiles()?.forEach {
        val dataFile = File(it.path, "data")
        val infoFile = File(it.path, "info")
        //

        val dtIn = dataFile.inputStream()
        val btmp = BitmapFactory.decodeStream(dtIn)
        dtIn.close()

        val infIn = infoFile.inputStream()
        val ois = ObjectInputStream(infIn)
        val obj = ois.readObject()
        ois.close()
        infIn.close()

        list.add(Pair(btmp, obj as MyPhoto))
    }

    return list
}