plugins {
    alias(libs.plugins.yandex.finance.android.library)
    alias(libs.plugins.yandex.finance.android.koin)
}

android {
    namespace = "com.yandex.finance.core.data"
}

dependencies {

    implementation(project(":core:domain"))
    implementation(project(":core:network"))
}