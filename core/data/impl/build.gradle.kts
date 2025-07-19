plugins {
    alias(libs.plugins.yandex.finance.android.library)
    alias(libs.plugins.yandex.finance.android.dagger)
}

android {
    namespace = "com.yandex.finance.core.data.impl"
}

dependencies {
    implementation(project(":core:data:api"))
    implementation(project(":core:domain"))
    implementation(project(":core:network:api"))
    implementation(project(":core:localdb"))

    /**
     * Date dependencies
     */
    // region datetime
    implementation(libs.kotlinx.datetime)
    // endregion
    
    // WorkManager
    implementation(libs.androidx.work.runtime.ktx)
    
    // Timber
    implementation(libs.jakewharton.timber)
} 