import com.android.build.gradle.LibraryExtension
import com.yandex.finance.build_logic.convention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("yandex.finance.android.library")
            }

            configureAndroidCompose(extensions.getByType<LibraryExtension>())
        }
    }
}
