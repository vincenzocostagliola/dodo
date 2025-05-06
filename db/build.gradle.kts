plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "dev.vincenzocostagliola.db"
    compileSdk = 35
}

dependencies {
    implementation(libs.androidx.core.ktx)

    api(libs.bundles.room)
    ksp(libs.room.compiler)

    /**HILT*/
    implementation (libs.hilt)
    ksp(libs.hilt.compiler)
}