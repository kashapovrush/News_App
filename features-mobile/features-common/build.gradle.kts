plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.features_common"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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

    implementation(project(":core:api"))
    implementation(project(":core:utils"))
    implementation(project(":features-mobile:palette"))
    implementation(project(":core:database"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Fragment manager
    implementation(libs.fragment.manager)



    //RxJava 3
    implementation (libs.rxjava.core)
    implementation (libs.rxjava.adapter)
    implementation (libs.rxjava.android)

    //Dagger2
    implementation (libs.dagger.core)
    ksp (libs.dagger.compiler)

    //For loading image
    implementation (libs.glide)

    //LiveData
    implementation (libs.lifecycle.livedata.ktx)

    //Moxy
    implementation (libs.moxy.core)
    kapt (libs.moxy.complier)
    implementation (libs.moxy.android)

    // Room database
    implementation (libs.room.core)
    ksp (libs.room.compiler)
    implementation (libs.room.runtime)
    implementation (libs.room.rxjava)

}