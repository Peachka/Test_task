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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.testtaskheartrate.R
import com.example.testtaskheartrate.ui.theme.dimens


@Composable
fun OnboardingPage2(
    onNextPage: () -> Unit,
    currentPageIndex: Int,
    onPageIndexChanged: (Int) -> Unit
) {Column(
    modifier = Modifier
        .fillMaxSize(),
//            .background(MaterialTheme.colorScheme.primary),
    verticalArrangement = Arrangement.SpaceBetween,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.onboarding_img_2),
            contentDescription = "Onboarding image 1",
            modifier = Modifier
                .size(200.dp)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium3))
        Text(
            text = "Персоналізовані поради",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Програма надає дієві поради, які допоможуть вам підтримувати оптимальний рівень артеріального тиску та зменшити фактори ризику серцево-судинних захворювань.",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        DotsIndicator(
            totalDots = 3,
            currentDotIndex = currentPageIndex
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
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
                shape = MaterialTheme.shapes.extraLarge, // Or any other desired shape
                onClick = {
                    onNextPage()
                    onPageIndexChanged(1)
                }
            ) {
                Text(
                    text = "Почати!",
                    modifier = Modifier.background(MaterialTheme.colorScheme.secondary)
                )
            }
        }
    }
}
}

@Preview
@Composable
fun PreviewPage1(){
    OnboardingPage1( 1, {},{ 1} )
}

