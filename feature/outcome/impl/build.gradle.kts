plugins {
    alias(libs.plugins.yandex.finance.android.library.compose)
    alias(libs.plugins.yandex.finance.android.library)
    alias(libs.plugins.yandex.finance.android.koin)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.yandex.finance.feature.outcome.impl"
}

dependencies {

    implementation(project(":core:ui"))
    implementation(project(":core:data:api"))
    implementation(project(":core:domain"))
    implementation(project(":core:common"))
    implementation(project(":feature:outcome:api"))

    /**
     * Core dependencies
     */
    // region core
    implementation(libs.androidx.core.ktx)
    // endregion

    /**
     * Date dependencies
     */
    // region datetime
    implementation(libs.kotlinx.datetime)
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
