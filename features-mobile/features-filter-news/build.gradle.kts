plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.features_filter_news"
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
//        isCoreLibraryDesugaringEnabled = true
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

    implementation(project(":features-mobile:palette"))
    implementation(project(":features-mobile:features-common"))
    implementation(project(":core:preferences"))
    implementation(project(":core:utils"))
    implementation(project(":core:network"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
//    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

    //Fragment manager
    implementation(libs.fragment.manager)

    //Dagger2
    implementation (libs.dagger.core)
    ksp (libs.dagger.compiler)

    //RxJava 3
    implementation (libs.rxjava.core)
    implementation (libs.rxjava.adapter)
    implementation (libs.rxjava.android)

    //Coroutine
    implementation (libs.coroutine.android)

    //Swipe refresh layout
    implementation(libs.swipe.refresh.layout)

    implementation(libs.jetpack.paging.common)
}