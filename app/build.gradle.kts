plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(28)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(28)
        applicationId = "com.dolezal.album"
        versionName = "1.0.0"
        versionCode = 1
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    dexOptions {
        javaMaxHeapSize = "2g"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
        }
        getByName("release") {
            isDebuggable = false
        }
    }
}

androidExtensions {
    isExperimental = true
}

kapt {
    arguments {
        arg("toothpick_registry_package_name", "com.dolezal.album")
    }
}

dependencies {
    // KOTLIN RUNTIME
    implementation(embeddedKotlin("stdlib"))

    // ANDROIDX -- JETPACK
    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation("androidx.recyclerview:recyclerview:1.0.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata:2.0.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.0.0")
    implementation("com.google.android.material:material:1.1.0-alpha08")

    // UI LIBRARIES
    implementation("com.google.android:flexbox:1.1.0")
    implementation("me.zhanghai.android.materialprogressbar:library:1.6.1")
    implementation("com.github.bumptech.glide:glide:4.9.0")
    implementation("com.github.bumptech.glide:okhttp3-integration:4.9.0")
    kapt("com.github.bumptech.glide:compiler:4.9.0")

    // NETWORKING LIBRARIES
    implementation("com.squareup.retrofit2:retrofit:2.5.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.5.0")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.13.1")

    // TOOTHPICK
    implementation("com.github.stephanenicolas.toothpick:toothpick-runtime:2.1.0")
    kapt("com.github.stephanenicolas.toothpick:toothpick-compiler:2.1.0")

    // RX JAVA
    implementation("io.reactivex.rxjava2:rxjava:2.2.7")
    implementation("io.reactivex.rxjava2:rxkotlin:2.3.0")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")

    // TESTING LIBRARIES
    implementation("com.facebook.stetho:stetho:1.5.1")
    implementation("com.facebook.stetho:stetho-okhttp3:1.5.1")

    testImplementation("junit:junit:4.12")
    testImplementation("org.mockito:mockito-core:2.13.0")

    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.1")
    androidTestImplementation("androidx.test:rules:1.1.1")
    androidTestImplementation("androidx.test:runner:1.1.1")
}