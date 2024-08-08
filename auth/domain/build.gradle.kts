plugins {
    alias(libs.plugins.pacepal.jvm.library)
}

dependencies{
    implementation(projects.core.domain)
}