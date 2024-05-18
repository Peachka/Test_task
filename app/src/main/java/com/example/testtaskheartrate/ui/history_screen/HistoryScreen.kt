package com.example.testtaskheartrate.ui.history_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testtaskheartrate.R
import com.example.testtaskheartrate.ui.utils.AppBackground

@Composable
fun HistoryScreen(
    viewModel: HistoryScreenViewModel = hiltViewModel(),
    goBackToHomeScreen: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.secondary,
                title = { Text("") },
                actions = {
                    IconButton(onClick = { }) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = "More options",
                                modifier = Modifier.clickable { goBackToHomeScreen() },
                                tint = Color.White
                            )
                            Text(
                                text = "Історія",
                                modifier = Modifier.padding(horizontal = 5.dp),
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.White
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        AppBackground {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(end = 16.dp)
                ) {
                    uiState.historyListItems.forEach { item ->
                        RecordCard(item)
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .padding(2.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = Color.White,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.White
                        ),
                        onClick = uiState.deleteAllRecords
                    ) {
                        Text(text = "Очистити історію")
                    }
                }

                val itemCount = uiState.historyListItems.size
                val itemHeight = 120.dp
                val visibleItems = 6

                val density = LocalDensity.current

                // Custom Scrollbar
                val proportion = scrollState.value.toFloat() / (scrollState.maxValue.toFloat() + 1)
                val totalHeightPx = with(density) { itemHeight.toPx() * itemCount }
                val viewportHeightPx = with(density) { itemHeight.toPx() * visibleItems }

                val scrollbarHeightPx = (viewportHeightPx / totalHeightPx) * viewportHeightPx
                val scrollbarHeight = with(density) { scrollbarHeightPx.toDp() }

                Box(
                    modifier = Modifier
                        .width(8.dp)
                        .fillMaxHeight()
                        .align(Alignment.CenterEnd)
                        .background(Color.Gray.copy(alpha = 0.5f))
                ) {
                    Box(
                        modifier = Modifier
                            .width(8.dp)
                            .height(scrollbarHeight)
                            .align(Alignment.TopStart)
                            .offset {
                                IntOffset(
                                    x = 0,
                                    y = (proportion * (scrollState.maxValue.toFloat() / itemCount)).toInt()
                                )
                            }
                            .background(MaterialTheme.colorScheme.secondary)
                    )
                }
            }
        }
    }
}

@Composable
private fun RecordCard(item: HistoryListItem) {

    Row(
        modifier = Modifier
            .height(120.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.onPrimary)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${item.heartRate} BPM",
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
            color = Color.Black,
            style = MaterialTheme.typography.bodyLarge
        )
        Divider(
            color = Color.Red,
            modifier = Modifier
                .width(2.dp)
                .height(40.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = item.timeDate,
                color = Color.Gray,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = item.timeDate,
                color = Color.Gray,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
fun PreviewHistoryScreen() {
    HistoryScreen(goBackToHomeScreen = {})
}
