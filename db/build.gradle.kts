plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "dev.vincenzocostagliola.db"
    compileSdk = 35
    compileOptions {
        defaultConfig {
            minSdk = 26
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_21.toString()
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)

    api(libs.bundles.room)
    ksp(libs.room.compiler)

    /**HILT*/
    implementation (libs.hilt)
    ksp(libs.hilt.compiler)
}