package com.example.testtaskheartrate.ui.onboarding_screens


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.material3.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.testtaskheartrate.R


@Composable
fun OnboardingPage2(
    onNextPage: () -> Unit,
    currentPageIndex: Int,
    onPageIndexChanged: (Int) -> Unit
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.onboarding_img_2),
            contentDescription = "Onboarding image 2",
            modifier = Modifier
                .size(200.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Персоналізовані поради",
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "Програма надає дієві поради, які допоможуть вам підтримувати оптимальний рівень артеріального тиску та зменшити фактори ризику серцево-судинних захворювань.",
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(216.dp))

        DotsIndicator(
            totalDots = 3,
            currentDotIndex = currentPageIndex
        )

        Row {
            Button(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(2.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White
                ),
                shape = MaterialTheme.shapes.large, // Or any other desired shape
                onClick = {
                    onNextPage()
                    onPageIndexChanged(2)
                }
            ) {
                Text(
                    text = "Продовжити",
                    Modifier.background(MaterialTheme.colorScheme.secondary)
                )
            }
        }
    }
}