import com.yandex.finance.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidDaggerConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kotlin-kapt")
            }

            dependencies {
                "implementation"(libs.findLibrary("dagger").get())
                "implementation"(libs.findLibrary("dagger-android").get())
                "kapt"(libs.findLibrary("dagger-compiler").get())
                "kapt"(libs.findLibrary("dagger-android-processor").get())
            }
        }
    }
}
