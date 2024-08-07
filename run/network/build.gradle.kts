plugins {
    alias(libs.plugins.pacepal.android.library)
}

android {
    namespace = "com.nolawiworkineh.run.network"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
}