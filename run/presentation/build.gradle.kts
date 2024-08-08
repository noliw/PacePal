plugins {
    alias(libs.plugins.pacepal.android.feature.ui)
}

android {
    namespace = "com.nolawiworkineh.run.presentation"
}

dependencies {
    implementation(projects.run.domain)
    implementation(projects.core.domain)
    implementation(libs.coil.compose)
    implementation(libs.google.maps.android.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.timber)
}