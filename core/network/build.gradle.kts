plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.kashapovrush.api"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(libs.google.gson)

    //RxJava 3
    implementation (libs.rxjava.core)
    implementation (libs.rxjava.adapter)
    implementation (libs.rxjava.android)

    //Coroutine
    implementation (libs.coroutine.android)

    implementation(libs.androidx.annotations)

    implementation(libs.dagger.core)
    ksp(libs.dagger.compiler)

    implementation(libs.jetpack.paging.common)
}