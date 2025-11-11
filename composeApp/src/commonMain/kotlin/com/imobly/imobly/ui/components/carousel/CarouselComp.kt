package com.imobly.imobly.ui.components.carousel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import io.github.ismoy.imagepickerkmp.domain.extensions.loadPainter
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CarouselComp(items: MutableState<List<GalleryPhotoResult>>) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val currentItem = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visible = layoutInfo.visibleItemsInfo
            val viewportCenter: Int = layoutInfo.viewportStartOffset + layoutInfo.viewportSize.width / 2

            if (visible.isEmpty()) 0 else {
                var closestIndex = visible[0].index
                var minDistance: Int = kotlin.math.abs((visible[0].offset + visible[0].size / 2) - viewportCenter)

                for (item in visible) {
                    val itemCenter: Int = item.offset + item.size / 2
                    val distance: Int = kotlin.math.abs(itemCenter - viewportCenter)
                    if (distance < minDistance) {
                        minDistance = distance
                        closestIndex = item.index
                    }
                }
                closestIndex
            }
        }
    }


if (items.value.isNotEmpty()) {

    Column(
        Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyRow(
            state = listState,
            modifier = Modifier
                .padding(20.dp)
                .width(500.dp)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        scope.launch { listState.scrollBy(-dragAmount) }
                    }
                },
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items.value) { photo ->
                Box(
                    modifier = Modifier
                        .size(250.dp, 280.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    photo.loadPainter()?.let {
                        Image(
                            painter = it,
                            contentDescription = "Imagem selecionada",
                            contentScale = ContentScale.FillHeight
                        )
                    }
                }
            }
        }
    }

    IndicatorBall(items.value.size, currentItem.value)
    }
}

@Composable
fun IndicatorBall(size: Int, actual: Int) {
    Row(
        Modifier
            .padding(5.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
        repeat(size) { index ->
            Box(
                modifier = Modifier
                    .background(
                        color = if (actual == index) PrimaryColor else Color.White,
                        shape = CircleShape
                    )
                    .border(1.dp, Color.Gray, CircleShape)
                    .size(15.dp)
                    .padding(5.dp)
            )
        }
    }
}

@Preview
@Composable
fun CarouselCompPreview() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CarouselComp(
            mutableStateOf(emptyList())
        )
    }
}
