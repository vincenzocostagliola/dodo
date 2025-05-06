plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.vincenzocostagliola.coindetails"
    testNamespace = "dev.vincenzocostagliola"
    compileSdk = 35
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.material3)
    /**HILT*/
    // For hilt Implementation
    implementation (libs.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.timber)
    /**SERIALIZATION*/
    implementation(libs.kotlin.serialization)

    /**DATE TIME*/
    implementation(libs.threeTen)
    /**NETWORK*/
    implementation(libs.bundles.network)
    /**MODULES*/
   // implementation(project(":data"))
    //implementation(project(":designsystem"))
}