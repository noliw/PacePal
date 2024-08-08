plugins {
    alias(libs.plugins.pacepal.android.feature.ui)
}

android {
    namespace = "com.nolawiworkineh.auth.presentation"
}

dependencies {
    implementation(projects.auth.domain)
    implementation(projects.core.domain)

}