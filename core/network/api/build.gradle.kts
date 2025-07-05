plugins {
    alias(libs.plugins.yandex.finance.android.library)
    kotlin("plugin.serialization")
}

android {
    namespace = "com.yandex.finance.core.network.api"
}

dependencies {
    implementation(project(":core:common"))
    
    /**
     * Date dependencies
     */
    implementation(libs.kotlinx.datetime)
    
    /**
     * Ktor dependencies for client interface
     */
    implementation(libs.ktor.client.core)
    
    /**
     * Kotlinx Serialization
     */
    implementation(libs.kotlinx.serialization.json)
} 