plugins {
    alias(libs.plugins.yandex.finance.android.library.compose)
    alias(libs.plugins.yandex.finance.android.library)
    alias(libs.plugins.yandex.finance.android.dagger)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.yandex.finance.feature.settings.impl"
}

dependencies {

    implementation(project(":core:ui"))
    implementation(project(":core:common"))
    implementation(project(":core:dagger"))
    implementation(project(":core:data:api"))
    implementation(project(":core:data:impl"))
    implementation(project(":feature:settings:api"))

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
