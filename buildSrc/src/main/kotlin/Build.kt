object Build {
    private const val buildGradleVersion = "7.0.2"
    const val buildGradleTools = "com.android.tools.build:gradle:$buildGradleVersion"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.kotlinVersion}"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Hilt.version}"
    const val sqlDelight = "com.squareup.sqldelight:gradle-plugin:${SqlDelight.sqlDelightVersion}"
}