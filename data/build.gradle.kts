plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.vincenzocostagliola.data"
    compileSdk = 35
}

dependencies {

    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    implementation(libs.timber)


    /**HILT*/
    // For hilt Implementation
    implementation (libs.hilt)
    ksp(libs.hilt.compiler)
}