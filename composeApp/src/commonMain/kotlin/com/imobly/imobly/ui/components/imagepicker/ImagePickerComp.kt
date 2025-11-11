package com.imobly.imobly.ui.components.imagepicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import io.github.ismoy.imagepickerkmp.domain.models.MimeType
import io.github.ismoy.imagepickerkmp.presentation.ui.components.GalleryPickerLauncher
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.collections.emptyList

@Composable
fun ImagePickerComp(label: String, stateImages: MutableState<List<GalleryPhotoResult>>, allowMultiple: Boolean = true) {
    val showGallery = remember { mutableStateOf(false) }
    val selectedImages = remember { stateImages }

    if (showGallery.value) {
        GalleryPickerLauncher(
            onPhotosSelected = { photos ->
                selectedImages.value = photos
                showGallery.value = false
            },
            onError = {
                showGallery.value = false
            },
            onDismiss = {
                println("User cancelled or dismissed the picker")
                showGallery.value = false
            },
            enableCrop = false,
            allowMultiple = allowMultiple,
            mimeTypes = listOf(MimeType.IMAGE_PNG),
        )
    }

    ButtonComp(
        label,
        { Icon(Icons.Default.Image, "Imagem") },
        PrimaryColor,
        { showGallery.value = true }
    )
}

@Preview
@Composable
fun ImagePickerCompPreview() {
    val stateFake = mutableStateOf<List<GalleryPhotoResult>>(emptyList())
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ImagePickerComp("Escolha a imagem", stateFake)
    }
}