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

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.google.android.gms.play.services.location)
}