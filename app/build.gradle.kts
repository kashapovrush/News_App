plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    id("kotlin-kapt")
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

    implementation(project(":core:network"))
    implementation(project(":core"))
    implementation(project(":core:database"))
    implementation(project(":features-mobile:features-headlines"))
    implementation(project(":features-mobile"))
    implementation(project(":core:utils"))
    implementation(project(":features-mobile:features-repository"))
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


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(libs.lifecycle.runtime)

    //Fragment manager
    implementation(libs.fragment.manager)


    //Moxy for MVP
    implementation (libs.moxy.core)
    kapt (libs.moxy.complier)
    implementation (libs.moxy.android)

    //Dagger2
    implementation (libs.dagger.core)
    ksp (libs.dagger.compiler)


}