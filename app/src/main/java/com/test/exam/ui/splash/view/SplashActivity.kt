package com.test.exam.ui.splash.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composetest.ui.theme.ComposetestTheme
import com.example.composetest.ui.theme.Purple200
import com.example.composetest.ui.theme.Purple700
import org.w3c.dom.Text

class SplashActivity :  AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowPreview()
        }
    }
}

@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }


@Composable
fun Greeting(name: String) {

    Text(text = "Hello $name!", fontSize = dpToSp(dp = 30.dp), color = Purple700)
}

@Preview(showBackground = true)
@Composable
fun ShowPreview() {
    ComposetestTheme() {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Greeting(name = "Wiiv")
            MyButton()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyButton() {
    ComposetestTheme {
        Column(
            Modifier.padding(10.dp)
        ) {
            TextButton(onClick = { /*TODO*/ }, modifier = Modifier.wrapContentSize()) {
                Text(text = "Button")
            }
            OutlinedButton(onClick = { /*TODO*/ }, modifier = Modifier.wrapContentSize()) {
                Text(text = "OutLinedButton", fontSize = dpToSp(dp = 20.dp))
            }
        }
    }
}



