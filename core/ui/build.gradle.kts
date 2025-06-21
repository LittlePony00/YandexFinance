plugins {
    alias(libs.plugins.yandex.finance.android.library.compose)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.yandex.finance.core.ui"
}

dependencies {

    implementation(project(":core:navigation"))

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
