import com.android.build.api.dsl.LibraryExtension
import com.nolawiworkineh.convention.ExtensionType
import com.nolawiworkineh.convention.addUiLayerDependencies
import com.nolawiworkineh.convention.configureAndroidCompose
import com.nolawiworkineh.convention.configureBuildTypes
import com.nolawiworkineh.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

class AndroidFeatureUiConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("pacepal.android.library.compose")
            }

            dependencies {
                addUiLayerDependencies(target)
            }
        }
    }
}