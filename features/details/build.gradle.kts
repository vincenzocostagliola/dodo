import com.android.build.api.dsl.Packaging
import org.gradle.kotlin.dsl.androidTestImplementation

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.vincenzocostagliola.home"
    testNamespace = "dev.vincenzocostagliola"
    compileSdk = 35
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
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
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.timber)
    /**SERIALIZATION*/
    implementation(libs.kotlin.serialization)

    implementation(libs.androidx.hilt.navigation.compose)


    /**DATE TIME*/
    implementation(libs.threeTenJW)
    testImplementation(libs.threeTen)

    /**NETWORK*/
    implementation(libs.bundles.network)
    /**MODULES*/
    implementation(project(":data"))
    implementation(project(":db"))
    implementation(project(":designsystem"))

    /**TEST**/
    implementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.androidimplementationtest)
}