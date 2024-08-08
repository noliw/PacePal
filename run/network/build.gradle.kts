plugins {
    alias(libs.plugins.pacepal.android.library)
    alias(libs.plugins.pacepal.jvm.ktor)
}

android {
    namespace = "com.nolawiworkineh.run.network"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
}