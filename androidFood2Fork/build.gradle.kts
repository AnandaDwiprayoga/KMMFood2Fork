plugins {
    id(Plugins.androidApplication)
    kotlin(KotlinPlugins.android)
    kotlin(KotlinPlugins.kapt)
    kotlin(KotlinPlugins.serialization) version Kotlin.kotlinVersion
    id(Plugins.hilt)
}

android {
    compileSdk = Application.compileSdk
    defaultConfig {
        applicationId = Application.applicationId
        minSdk = Application.minSdk
        targetSdk = Application.targetSdk
        versionCode = Application.versionCode
        versionName = Application.versionName
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.0-rc01"//Compose.composeVersion <- it's deprecated method
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(AndroidX.appCompact)

    implementation(Compose.runtime)
    implementation(Compose.runtimeLiveData)
    implementation(Compose.ui)
    implementation(Compose.material)
    implementation(Compose.uiTooling)
    implementation(Compose.foundation)
    implementation(Compose.compiler)
    implementation(Compose.constraintLayout)
    implementation(Compose.activity)
    implementation(Compose.navigation)
    implementation(SplashScreen.androidSplashScreen)

    implementation(Hilt.hiltAndroid)
    implementation(Hilt.navigationCompose)
    kapt(Hilt.hiltCompiler)

    implementation(Ktor.android)

    implementation(Coil.coil)

    implementation(Google.material)
    implementation(Kotlinx.datetime)
    debugImplementation(SquareUp.leakCanary)

}