plugins {
    alias(libs.plugins.yandex.finance.android.library)
}

android {
    namespace = "com.yandex.finance.core.data.api"
}

dependencies {

    implementation(project(":core:domain"))
}
