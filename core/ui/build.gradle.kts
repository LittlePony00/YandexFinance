plugins {
    alias(libs.plugins.yandex.finance.android.library.compose)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.yandex.finance.core.ui"
}

dependencies {

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
