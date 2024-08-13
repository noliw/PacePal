plugins {
    alias(libs.plugins.pacepal.android.library)
}

android {
    namespace = "com.nolawiworkineh.run.location"
}

dependencies {
    implementation(projects.run.domain)
    implementation(projects.core.domain)
    implementation(libs.androidx.core.ktx)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.google.android.gms.play.services.location)
}