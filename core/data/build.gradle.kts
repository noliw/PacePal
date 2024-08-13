plugins {
    alias(libs.plugins.pacepal.android.library)
    alias(libs.plugins.pacepal.jvm.ktor)

}

android {
    namespace = "com.nolawiworkineh.core.data"
}

dependencies {
    implementation(libs.timber)
    implementation(projects.core.database)
    implementation(projects.core.domain)
}