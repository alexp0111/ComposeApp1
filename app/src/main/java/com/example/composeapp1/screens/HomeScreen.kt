package com.example.composeapp1.screens

import android.graphics.Bitmap
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.composeapp1.MyApplication
import com.example.composeapp1.R
import com.example.composeapp1.data.MyPhoto
import com.example.composeapp1.utils.DiskLoader
import com.example.composeapp1.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                    DiskLoader.loadFromMemo(mainViewModel.getApplication<MyApplication>().applicationContext)
                mainViewModel.updatePhotos(newList)
            }
            items(
                cards.value.sortedWith(
                    compareBy(
                        { it.second.date },
                        { it.second.time })
                )
            ) { item ->
                ImageRow(item = item)
            }
        }
    }
}

@Composable
fun ImageRow(item: Pair<Bitmap, MyPhoto>) {
    Card(
        modifier = Modifier
            .padding(
                start = dimensionResource(id = R.dimen.basic_padding),
                end = dimensionResource(id = R.dimen.basic_padding),
                top = dimensionResource(id = R.dimen.half_padding),
                bottom = dimensionResource(id = R.dimen.half_padding)
            )
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
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(Modifier.size(dimensionResource(id = R.dimen.spacer)))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimensionResource(id = R.dimen.basic_padding),
                        bottom = dimensionResource(id = R.dimen.basic_padding)
                    ),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = item.second.time)
                Text(text = item.second.date)
            }
        }
    }
}