plugins {
    alias(libs.plugins.pacepal.android.library.compose)
}

android {
    namespace = "com.nolawiworkineh.core.presentation.designsystem"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.ui.graphics.android)
    implementation(libs.androidx.material3.android)
    debugImplementation(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.material3)
}