plugins {
    alias(libs.plugins.yandex.finance.android.library)
    alias(libs.plugins.yandex.finance.android.dagger)
    id("kotlin-kapt")
}

android {
    namespace = "com.yandex.finance.core.localdb"
}

dependencies {
    implementation(project(":core:domain"))
    
    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
    
    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    
    // Date/Time
    implementation(libs.kotlinx.datetime)
    
    // Timber for logging
    implementation(libs.jakewharton.timber)
} 