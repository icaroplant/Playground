
plugins{
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}



android {
    compileSdk = 31

    defaultConfig {
        minSdk = 26
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release")  {
            isMinifyEnabled = false
        }
    }

    dataBinding.isEnabled = true
    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }

    lintOptions.disable("MissingDefaultResource")

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
        //animationsDisabled = true
    }

    packagingOptions {
        resources.excludes += "META-INF/DEPENDENCIES"
        resources.excludes += "META-INF/NOTICE"
        resources.excludes += "META-INF/LICENSE"
        resources.excludes += "META-INF/LGPL2.1"
        resources.excludes += "META-INF/AL2.0"
        resources.excludes += "META-INF/ASL2.0"
        resources.excludes += "META-INF/NOTICE"
    }

}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.10")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    //Navigation
    val navigation = "2.4.1"
    implementation("androidx.navigation:navigation-fragment-ktx:$navigation")
    implementation("androidx.navigation:navigation-ui-ktx:$navigation")

    //Room
    val room = "2.4.2"
    implementation("androidx.room:room-runtime:$room")
    kapt("androidx.room:room-compiler:$room")
    implementation("androidx.room:room-ktx:$room")

    // Koin for Android
    val koin = "3.1.6"
    val koinExt = "3.0.2"
    val koinAndroidxExt = "2.2.3"
    implementation("io.insert-koin:koin-core:$koin")
    implementation("io.insert-koin:koin-core-ext:$koinExt")
    implementation("io.insert-koin:koin-android-compat:$koin")
    implementation("io.insert-koin:koin-android:$koin")
    implementation("io.insert-koin:koin-androidx-ext:$koinAndroidxExt")

    //coroutines
    val coroutine = "1.5.2"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutine")

    //Mockk
    val mockk = "1.9.3"
    implementation("io.mockk:mockk:$mockk")
    implementation("io.mockk:mockk-android:$mockk")
    androidTestImplementation("io.mockk:mockk-android:$mockk")

    //Test
    testImplementation("junit:junit:4.13")
    testImplementation("org.robolectric:robolectric:4.4")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.3.0")
    //androidTestImplementation("io.insert-koin:koin-test-junit4:3.0.2")
    debugImplementation("androidx.fragment:fragment-testing:1.3.3")

    //Permission Dispatcher
    val permission_dispatcher = "4.8.0"
    implementation("com.github.permissions-dispatcher:permissionsdispatcher:$permission_dispatcher")
    kapt("com.github.permissions-dispatcher:permissionsdispatcher-processor:$permission_dispatcher")

    //Gson
    val gson = "2.8.6"
    implementation("com.google.code.gson:gson:$gson")

    //ImageViewers
    val photoview = "2.0.0"
    implementation("com.github.chrisbanes:PhotoView:$photoview")

    //Glide
    val glide = "4.11.0"
    implementation("com.github.bumptech.glide:glide:$glide")

    //CaseChanger
    implementation("com.github.KenjiOhtsuka:kotlin-case-changer:0.1.1")
    
    //Compose
    // Integration with activities
    implementation("androidx.activity:activity-compose:1.4.0")
    // Compose Material Design
    implementation("androidx.compose.material:material:1.1.1")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation("androidx.compose.foundation:foundation:1.1.1")
    // Material design icons
    implementation("androidx.compose.material:material-icons-core:1.1.1")
    implementation("androidx.compose.material:material-icons-extended:1.1.1")
    // Animations
    implementation("androidx.compose.animation:animation:1.1.1")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:1.1.1")
    // Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1")
    // When using a AppCompat theme
    implementation("com.google.accompanist:accompanist-appcompat-theme:0.16.0")

}