import com.android.build.gradle.LibraryExtension
import com.yandex.finance.build_logic.convention.configureKotlinAndroid
import com.yandex.finance.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 34
            }

            dependencies {
                "testImplementation"(libs.findLibrary("junit").get())
                "androidTestImplementation"(libs.findLibrary("junit").get())
                "testImplementation"(libs.findLibrary("androidx-test-ext-junit-ktx").get())
                "androidTestImplementation"(libs.findLibrary("androidx-test-ext-junit-ktx").get())
                "implementation"(libs.findLibrary("jakewharton-timber").get())
            }
        }
    }
}
