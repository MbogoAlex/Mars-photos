package com.example.marsphotos

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.Coil
import coil.compose.AsyncImage
import com.example.marsphotos.model.MarsPhoto
import com.example.marsphotos.ui.appViewModel.AppViewModel
import com.example.marsphotos.ui.state.MarsUiState
import com.example.marsphotos.ui.theme.MarsPhotosTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: AppViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    when(uiState.marsUiState) {
        is MarsUiState.Loading -> Loading(modifier = Modifier.fillMaxSize())
        is MarsUiState.Success -> MarsPhotos(marsPhotos = (uiState.marsUiState as MarsUiState.Success).marsPhotos)
        is MarsUiState.Error -> Error()
    }
}

@Composable
fun MarsPhotos(
    marsPhotos: List<MarsPhoto>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(marsPhotos.size) { index ->
            MarsPhotoItem(
                marsPhoto = marsPhotos[index],
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }
}

@Composable
fun MarsPhotoItem(
    marsPhoto: MarsPhoto,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = marsPhoto.imgSrc,
        placeholder = painterResource(id = R.drawable.loading_img),
        error = painterResource(id = R.drawable.ic_broken_image),
        contentDescription = "Mars photo",
        contentScale = ContentScale.FillWidth,
        modifier = modifier
    )
}

@Composable
fun Loading(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.loading_img),
        contentDescription = "Loading",

        modifier = modifier
            .size(200.dp)

    )
}

@Composable
fun Error(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = "Failed to load images"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenCompactPreview(
    modifier: Modifier = Modifier
) {
    MarsPhotosTheme {
        HomeScreen()
    }
}

