plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    id("kotlin-kapt")
}

android {
    namespace = "com.kashapovrush.headlines_features"
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

    buildFeatures {
        viewBinding = true
    }

}

dependencies {

    implementation(project(":core:api"))
    implementation(project(":core:utils"))
    implementation(project(":core:database"))
    implementation(project(":features-mobile:features-common"))
    implementation(project(":features-mobile:palette"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //RxJava 3
    implementation (libs.rxjava.core)
    implementation (libs.rxjava.adapter)
    implementation (libs.rxjava.android)

    //Swipe refresh layout
    implementation(libs.swipe.refresh.layout)


    //Fragment manager
    implementation(libs.fragment.manager)

    //Dagger2
    implementation (libs.dagger.core)
    ksp (libs.dagger.compiler)

    //For image
    implementation (libs.glide)

    //Moxy
    implementation (libs.moxy.core)
    kapt (libs.moxy.complier)
    implementation (libs.moxy.android)

    // Room database
    implementation (libs.room.core)
    ksp (libs.room.compiler)
    implementation (libs.room.runtime)
}
