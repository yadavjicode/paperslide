plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
  

}

android {
    namespace = "com.example.paper_slide"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.paper_slide"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
        viewBinding =true

    }

}

dependencies {

    implementation("com.google.android.gms:play-services-auth:20.7.0")
    // implementation("com.facebook.android:facebook-login:latest.release")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
   testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.7.2")
    implementation("com.squareup.retrofit2:converter-gson:2.7.2")

    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.5.0")

    //Dagger
    implementation("com.google.dagger:hilt-android:2.48")
    //kapt("com.google.dagger:hilt-android-compiler:2.48")

    //coroutines
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")

    //UnitTesting
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("com.google.truth:truth:1.0.1")
    testImplementation("com.google.truth:truth:1.0.1")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

    //FFMPEG
//    implementation ("com.arthenica:mobile-ffmpeg:2.4.4")

    //Cloudinary
//    implementation("com.github.mohamed0017:SimpleVideoEditor:2.2.2")
    // implementation ("com.cloudinary:kotlin-url-gen:1.6.0")
    implementation ("com.cloudinary:cloudinary-android:2.3.1")
    implementation ("com.squareup.picasso:picasso:2.8")
    implementation ("com.facebook.android:facebook-android-sdk:16.2.0")
    //crop_image
    implementation ("com.github.dhaval2404:imagepicker:2.1")
    //ocr
    implementation ("com.google.android.gms:play-services-mlkit-text-recognition:19.0.0")

    //textedit
    implementation("com.mohamedrejeb.richeditor:richeditor-compose:1.0.0-beta03")

    //signature

    implementation("com.kyanogen.signatureview:signature-view:1.2")

    implementation ("com.github.irshulx:laser-native-editor:3.0.3")





}