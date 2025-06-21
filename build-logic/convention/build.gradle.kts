import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    `kotlin-dsl`
}

group = "com.yandex.finance.build_logic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
    }
}

dependencies {
    compileOnly(libs.gradle.plugin.android)
    compileOnly(libs.gradle.plugin.kotlin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "yandex.finance.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidLibrary") {
            id = "yandex.finance.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidTimber") {
            id = "yandex.finance.android.timber"
            implementationClass = "AndroidTimberConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "yandex.finance.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidKtor") {
            id = "yandex.finance.android.ktor"
            implementationClass = "AndroidKtorConventionPlugin"
        }

        register("androidKoin") {
            id = "yandex.finance.android.koin"
            implementationClass = "AndroidKoinConventionPlugin"
        }
    }
}
