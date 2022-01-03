package com.pasukanlangit.id.food2fork.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import coil.annotation.ExperimentalCoilApi
import com.pasukanlangit.id.food2fork.android.presentation.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalStdlibApi
@AndroidEntryPoint
@ExperimentalComposeUiApi
@ExperimentalCoilApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            Navigation()
        }
    }
}
