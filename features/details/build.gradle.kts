plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "dev.vincenzocostagliola.details"
    compileSdk = 35
}

dependencies {

    implementation(libs.androidx.core.ktx)
}