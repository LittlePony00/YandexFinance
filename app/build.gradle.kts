plugins {
    alias(libs.plugins.yandex.finance.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.yandex.finance"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.yandex.finance"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {

    /**
     * Core dependencies
     */
    // region core
    implementation(libs.androidx.core.ktx)
    // endregion

    /**
     * Navigation dependencies
     */
    // region navigation
    implementation(libs.androidx.navigation.compose)
    // endregion

    /**
     * Serialization dependencies
     */
    // region serialization
    implementation(libs.kotlinx.serialization.json)
    // endregion

    /**
     * Lottie dependencies
     */
    // region lottie
    implementation(libs.airbnb.lottie.compose)
    // endregion
}
