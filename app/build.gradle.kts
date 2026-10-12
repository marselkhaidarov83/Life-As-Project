plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.huawei.agconnect")
}

android {
    signingConfigs {
        create("release") {
        }

        create("debugMyRustore") {
            storeFile = file("/Users/marselhaidarov/Library/Mobile Documents/com~apple~CloudDocs/Android LifeIsProject/Key Life As Project/Untitled")
            storePassword = "0wtTs1uoXix"
            keyAlias = "Life As Project"
            keyPassword = "U205x2V4rA1"
        }
    }

    namespace = "com.MH83.LifeAsProject"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.MH83.LifeAsProject"
        minSdk = 24
        targetSdk = 33
        versionCode = 28
        versionName = "1.03"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("debugMyRustore")
        }
        debug {
            signingConfig = signingConfigs.findByName("debugMyRustore")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies{
    //noinspection GradleCompatible
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.4")
    implementation("com.google.android.material:material:1.13.0")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("com.davemorrissey.labs:subsampling-scale-image-view:3.10.0")
    implementation("ru.rustore.sdk:billingclient:5.0.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.my.tracker:mytracker-sdk:3.3.2")
    implementation("com.android.billingclient:billing:7.0.0")
    implementation("androidx.work:work-runtime:2.10.1")
    implementation("org.jsoup:jsoup:1.18.1")
    implementation("androidx.viewpager2:viewpager2:1.1.0")
    implementation("me.relex:circleindicator:2.1.6")
//    implementation("com.huawei.hms:iap:6.10.0.300")
//    implementation("com.huawei.agconnect:agconnect-core:1.9.1.300")
}