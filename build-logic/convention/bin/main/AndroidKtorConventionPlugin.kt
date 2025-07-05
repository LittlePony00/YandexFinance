import com.yandex.finance.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidKtorConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                "implementation"(libs.findLibrary("ktor-client-core").get())
                "implementation"(libs.findLibrary("ktor-client-okhttp").get())
                "implementation"(libs.findLibrary("ktor-client-android").get())
                "implementation"(libs.findLibrary("ktor-client-logging").get())
                "implementation"(libs.findLibrary("ktor-client-serialization").get())
                "implementation"(libs.findLibrary("ktor-client-content-negotiation").get())
                "implementation"(libs.findLibrary("ktor-serialization-kotlinx-json").get())
                "implementation"(libs.findLibrary("kotlinx-serialization-json").get())
                "implementation"(libs.findLibrary("org-slf4j-api").get())
                "implementation"(libs.findLibrary("org-slf4j-simple").get())
            }
        }
    }
}
