plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "Mercury.Android"
    compileSdk = 36

    defaultConfig {
        applicationId = "Mercury.Android"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures{
        viewBinding = true
    }

}

dependencies {
    implementation(libs.blurview)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.recyclerview)

    //Lottie - Biblioteca Debug para detectação de Memory Leaks;
    implementation(libs.blurview)

    //Lottie - Biblioteca Debug para detectação de Memory Leaks;
    debugImplementation(libs.leakcanary.android)

    //Lottie - Biblioteca para Animações;
    implementation(libs.lottie)

    //Retrofit - Bibliotecas para Requisição REST;
    implementation(libs.retrofit.v2110)
    implementation(libs.converter.gson)
    implementation(libs.gson)

}