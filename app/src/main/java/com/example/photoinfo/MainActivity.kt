package com.example.photoinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.photoinfo.ui.theme.PhotoInfoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoInfoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content() {
    var pageIndex by remember { mutableStateOf(0) }

    val imageResource = when (pageIndex) {
        1 -> R.drawable.triangulo
        2 -> R.drawable.rectangulo
        else -> R.drawable.circulo
    }
    val title = when (pageIndex) {
        1 -> R.string.triangle
        2 -> R.string.rectangle
        else ->R.string.circle
    }

    val description = when (pageIndex) {
        1 -> R.string.triangleDescription
        2 -> R.string.rectangleDescription
        else ->R.string.circleDescription
    }
     Column(
            modifier = Modifier
                .padding(40.dp)
                .pointerInput(Unit){
                    detectHorizontalDragGestures { change, _ ->
                        if (change.positionChange().x < 0) {
                            if(pageIndex > 0) pageIndex -= 1
                            else if(pageIndex == 0) pageIndex = 2
                        } else if (change.positionChange().x > 0) {
                            if(pageIndex < 2) pageIndex += 1
                            else if(pageIndex == 2) pageIndex = 0
                        }
                        change.consume()
                    }
                }
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ShowImage(imageResource)
            Spacer(modifier = Modifier.height(32.dp))
            ShowDescription(title,description)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    if(pageIndex > 0) pageIndex -= 1
                    else if(pageIndex == 0) pageIndex = 2
                }
                ) {
                    Text(stringResource(R.string.previous))
                }
                Spacer(modifier = Modifier.weight(4f))
                Button(onClick = {
                    if(pageIndex < 2) pageIndex += 1
                    else if(pageIndex == 2) pageIndex = 0
                }) {
                    Text(stringResource(R.string.next))
                }
            }
    }
}

@Composable
fun ShowImage(
    @DrawableRes image: Int,
){
    Box(
        modifier = Modifier
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
        )
    }
}

@Composable
fun ShowDescription(
    @StringRes title: Int,
    @StringRes description: Int,
){


    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = stringResource(title),
                style = MaterialTheme.typography.displaySmall
            )
            Text(
                text = stringResource(description),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.weight(9f))
        }
    }
}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PhotoInfoTheme {
        Content()
    }
}