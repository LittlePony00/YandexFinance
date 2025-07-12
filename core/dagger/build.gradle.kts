plugins {
    alias(libs.plugins.yandex.finance.android.library)
    alias(libs.plugins.yandex.finance.android.dagger)
}

android {
    namespace = "com.yandex.finance.core.dagger"
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.androidx.lifecycle.viewmodel.android)
} 