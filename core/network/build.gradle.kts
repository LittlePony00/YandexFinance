import java.util.Properties

plugins {
    alias(libs.plugins.yandex.finance.android.library)
    alias(libs.plugins.yandex.finance.android.koin)
    alias(libs.plugins.yandex.finance.android.ktor)
    alias(libs.plugins.kotlin.compose)
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(localPropertiesFile.inputStream())
    }
}

android {
    namespace = "com.yandex.finance.core.network"

    defaultConfig {
        buildConfigField("String", "API_TOKEN", "\"${localProperties.getProperty("API_TOKEN", "")}\"")
        buildConfigField("String", "ID", "\"${localProperties.getProperty("ID", "")}\"")
        buildConfigField("String", "USER_ID", "\"${localProperties.getProperty("USER_ID", "")}\"")
        buildConfigField("String", "BASE_URL", "\"https://shmr-finance.ru/api/v1/\"")
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(project(":core:common"))

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.ktor.client.core)

    /**
     * Date dependencies
     */
    // region datetime
    implementation(libs.kotlinx.datetime)
    // endregion
}
