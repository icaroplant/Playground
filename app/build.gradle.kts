
plugins{
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}



android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.2")

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release")  {
            setMinifyEnabled(false)
            consumerProguardFiles("proguard-android-optimize.txt","proguard-rules.pro")
        }
    }

    dataBinding.isEnabled = true

    lintOptions.disable("MissingDefaultResource")

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.30")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.2")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    //Navigation
    val nav_version = "2.2.2"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    //Room
    val room_version = "2.2.5"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    // Koin for Android
    val koin_version = "2.0.1"
    implementation("org.koin:koin-android:$koin_version")
    implementation("org.koin:koin-androidx-scope:$koin_version")
    implementation("org.koin:koin-androidx-viewmodel:$koin_version")
    //implementation("org.koin:koin-androidx-fragment:$koin_version"

    //coroutines
    val coroutines_version = "1.4.2"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version")

    //Mockk
    val mock_version = "1.9.3"
    implementation("io.mockk:mockk:$mock_version")
    implementation("io.mockk:mockk-android:$mock_version")

    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

}