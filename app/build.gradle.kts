import java.util.Properties

plugins {
    alias(libs.plugins.yandex.finance.android.application)
    alias(libs.plugins.yandex.finance.android.koin)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(localPropertiesFile.inputStream())
    }
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
     *  Module dependencies
     */
    // region module dependencies
    implementation(project(":core:ui"))
    implementation(project(":core:data:impl"))
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:network:impl"))
    implementation(project(":feature:income:api"))
    implementation(project(":feature:income:impl"))
    implementation(project(":feature:outcome:api"))
    implementation(project(":feature:outcome:impl"))
    implementation(project(":feature:account:api"))
    implementation(project(":feature:account:impl"))
    implementation(project(":feature:settings:api"))
    implementation(project(":feature:settings:impl"))
    implementation(project(":feature:articles:api"))
    implementation(project(":feature:articles:impl"))
    // endregion

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
