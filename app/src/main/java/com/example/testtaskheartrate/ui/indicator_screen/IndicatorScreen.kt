package com.example.testtaskheartrate.ui.indicator_screen

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testtaskheartrate.R
import com.chargemap.compose.numberpicker.*
import com.example.testtaskheartrate.data.model.BPMHistory
import com.example.testtaskheartrate.ui.theme.Roboto
import com.example.testtaskheartrate.ui.utils.AppBackground

@Composable
fun IndicatorScreen(
    viewModel: IndicatorViewModel = hiltViewModel(),
    goToHomePage: () -> Unit
) {

    val dateTime by viewModel.dateTime.collectAsState()
    val (time, date) = dateTime.split(" ")


    val cornerRadius = CornerRadius(60f, 60f)
    val lightblueColor = MaterialTheme.colorScheme.onPrimary

    var pickerValue by remember { mutableStateOf(60) }


    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.secondary,
                title = { Text("") },
                actions = {
                    // Add your top app bar actions here
                    IconButton(onClick = { goToHomePage() }) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {

                            Icon(
                                painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = "More options",
                                tint = Color.White
                            )
                            Text(
                                text = "Новий запис",
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(70.dp))
                    

                    BPMIndicator(paddingValues = paddingValues,
                        blueColor = lightblueColor,
                        cornerRadius = cornerRadius,
                        pickerValue = pickerValue,
                        onPickerValueChange = { newValue ->
                            pickerValue = newValue
                        })

                    Text(
                        text = "Дата та Час",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 15.dp),
                        textAlign = TextAlign.Left
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(lightblueColor)
                                .weight(0.5f)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_date),
                                contentDescription = "date",
                                modifier = Modifier
                                    .size(35.dp)
                                    .padding(5.dp)
                            )
                            Text(date)
                        }

                        Spacer(modifier = Modifier.width(30.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(lightblueColor)
                                .weight(0.5f)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_time),
                                contentDescription = "date",
                                modifier = Modifier
                                    .size(35.dp)
                                    .padding(5.dp)
                            )
                            Text(time)
                        }
                    }


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
                    onClick = {
                        viewModel.addRecord(
                            BPMHistory(
                                heartRate = pickerValue,
                                timeDate = "$time $date"
                            )
                        )
                    }) {
                    Text(text = "Зберегти")
                }
            }
        }
    }
}


@Composable
private fun BPMIndicator(
    paddingValues: PaddingValues,
    blueColor: Color,
    cornerRadius: CornerRadius,
    pickerValue: Int,
    onPickerValueChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly


    ) {


        Box(
            modifier = Modifier
                .width(200.dp)
                .height(330.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                // Draw the blue top area
                drawRoundRect(
                    color = blueColor,
                    topLeft = Offset(0f, 0f),
                    size = Size(canvasWidth, canvasHeight * 1f),
                    cornerRadius = cornerRadius
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val range = 60..120
                Text(
                    text = "Пульс",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "(BPM)",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.labelMedium
                )
                ListItemPicker(
//                        modifier = Modifier.size(150.dp),

                    dividersColor = Color.Black.copy(0.8f),
                    value = pickerValue,
                    list = range.toList(),
                    onValueChange = { newValue ->
                        onPickerValueChange(newValue)
                    },
                    textStyle = TextStyle(
                        fontFamily = Roboto,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 55.sp
                    ),
                )
            }


        }


        Log.e("pickerValue = ", pickerValue.toString())
    }
}

//@Preview
//@Composable
//fun IndicatorScreenPreview() {
//
//
//    IndicatorScreen(viewModel = hiltViewModel(), {})
//}

