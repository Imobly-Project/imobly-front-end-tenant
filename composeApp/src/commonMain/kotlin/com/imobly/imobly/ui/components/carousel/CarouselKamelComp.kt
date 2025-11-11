package com.imobly.imobly.ui.components.carousel

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import com.imobly.imobly.domain.Property
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.abs

@Composable
fun CarouselKamelComp(pathImages: MutableState<Property>) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val currentItem = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visible = layoutInfo.visibleItemsInfo
            val viewportCenter: Int = layoutInfo.viewportStartOffset + layoutInfo.viewportSize.width / 2

            if (visible.isEmpty()) 0 else {
                var closestIndex = visible[0].index
                var minDistance: Int = abs((visible[0].offset + visible[0].size / 2) - viewportCenter)

                for (item in visible) {
                    val itemCenter: Int = item.offset + item.size / 2
                    val distance: Int = abs(itemCenter - viewportCenter)
                    if (distance < minDistance) {
                        minDistance = distance
                        closestIndex = item.index
                    }
                }
                closestIndex
            }
        }
    }

    if (pathImages.value.pathImages.isNotEmpty()) {
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
                items(pathImages.value.pathImages) { photo ->
                    Box(
                        modifier = Modifier
                            .size(250.dp, 280.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        KamelImage(
                            resource = { asyncPainterResource(photo) },
                            contentDescription = "Imagem do imóvel",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                            contentScale = ContentScale.Crop,
                            onLoading = { progress -> CircularProgressIndicator({ progress }) },
                            onFailure = { }
                        )

                    }
                }
            }
        }
        IndicatorBall(pathImages.value.pathImages.size, currentItem.value)
    }
}


@Preview
@Composable
fun CarouselKamelCompPreview() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CarouselKamelComp(
            mutableStateOf(Property())
        )
    }
}
