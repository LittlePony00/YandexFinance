plugins {
    alias(libs.plugins.yandex.finance.android.library.compose)
    alias(libs.plugins.yandex.finance.android.library)
    alias(libs.plugins.yandex.finance.android.koin)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.yandex.finance.feature.articles.api"
}

dependencies {

    implementation(project(":core:ui"))
    implementation(project(":core:data:api"))
    implementation(project(":core:common"))
    implementation(project(":core:dagger"))

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
}
