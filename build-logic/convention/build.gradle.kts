plugins {
    `kotlin-dsl`
}

group = "com.nolawiworkineh.pacepal.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "pacepal.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
    }
}