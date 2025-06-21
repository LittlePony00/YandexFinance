import com.yandex.finance.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidKoinConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            dependencies {
                "implementation"(libs.findLibrary("io-insert-koin-core").get())
                "implementation"(libs.findLibrary("io-insert-koin-androidx").get())
                "implementation"(libs.findLibrary("io-insert-koin-androidx-compose").get())
            }
        }
    }
}
