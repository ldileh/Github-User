plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.ldileh.githubuser"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ldileh.githubuser"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures{
        viewBinding = true
        buildConfig = true
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"

            buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
            buildConfigField("Boolean", "ENABLE_LOGGING", "true")
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
            buildConfigField("Boolean", "ENABLE_LOGGING", "true")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    viewBinding{
        enable = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.kotlinxMetadataJvm)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // sandwich
    implementation(libs.sandwich)
    implementation(libs.sandwich.retrofit)
    implementation(libs.sandwich.retrofit.serialization)

    // OkHttp and Logging Interceptor
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // Dagger Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Timber (Logging)
    implementation(libs.timber)

    // KSP
    implementation(libs.ksp)

    // chucker
    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)

    // coil
    implementation(libs.coil)

    // paging 3
    implementation(libs.paging3)

    // AndroidX Lifecycle (ViewModel, LiveData)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.runtime)

    // room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)

    // unit testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}