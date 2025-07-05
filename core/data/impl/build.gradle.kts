plugins {
    alias(libs.plugins.yandex.finance.android.library)
    alias(libs.plugins.yandex.finance.android.koin)
}

android {
    namespace = "com.yandex.finance.core.data.impl"
}

dependencies {
    implementation(project(":core:data:api"))
    implementation(project(":core:domain"))
    implementation(project(":core:network:api"))

    /**
     * Date dependencies
     */
    // region datetime
    implementation(libs.kotlinx.datetime)
    // endregion
} 