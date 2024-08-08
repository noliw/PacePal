plugins {
    alias(libs.plugins.pacepal.android.library)
    alias(libs.plugins.pacepal.jvm.ktor)
}

android {
    namespace = "com.nolawiworkineh.auth.data"
}

dependencies {
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}