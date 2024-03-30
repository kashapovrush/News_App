plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
}

android {
    namespace = "com.kashapovrush.newsapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kashapovrush.newsapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    implementation(project(":features-mobile:features-headlines"))
    implementation(project(":core:utils"))
    implementation(project(":features-mobile:features-common"))
    implementation(project(":features-mobile:palette"))
    implementation(project(":features-mobile:features-filter-news"))
    implementation(project(":features-mobile:features-search"))
    implementation(project(":features-mobile:features-news-post"))
    implementation(project(":core:preferences"))
    implementation(project(":features-mobile:palette"))
    implementation(project(":features-mobile:features-favourite"))
    implementation(project(":features-mobile:features-source"))
    implementation(project(":features-mobile:error"))
    implementation(project(":features-mobile:features-splash-screen"))


    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//
//    //Splash API
//    implementation("androidx.core:core-splashscreen:1.0.1")

    //Fragment manager
    implementation("androidx.fragment:fragment-ktx:1.6.2")

//    //Retrofit2
//    implementation("com.google.code.gson:gson:2.10.1")
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

//    //RxJava 3
//    implementation ("io.reactivex.rxjava3:rxjava:3.1.5")
//    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
//    implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")

//    //For loading image
//    implementation ("com.github.bumptech.glide:glide:4.16.0")

    //Moxy for MVP
    implementation ("com.github.moxy-community:moxy:2.2.2")
    annotationProcessor ("com.github.moxy-community:moxy-compiler:2.2.2")
    implementation ("com.github.moxy-community:moxy-androidx:2.2.2")

    //Dagger2
    implementation ("com.google.dagger:dagger:2.48.1")
    kapt ("com.google.dagger:dagger-compiler:2.48.1")

//    //ViewModel
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
//    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
//    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

}