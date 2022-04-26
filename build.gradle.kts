// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
    dependencies {
        val agp = "7.1.1"
        val kotlin = "1.5.32"
        classpath("com.android.tools.build:gradle:$agp")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")

        val navigation = "2.4.1"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navigation")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
    configurations.all {
        // Atualização do Koin
        exclude(group = "io.insert-koin", module = "koin-androidx-scope")
        exclude(group = "io.insert-koin", module = "koin-androidx-viewmodel")
        exclude(group = "com.linkedin.dexmaker", module = "dexmaker")
        // Substituído por io.insert-koin
        exclude(group = "org.koin")
    }
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
        kotlinOptions {
            jvmTarget = "11"
            freeCompilerArgs = listOf("-Xjvm-default=compatibility")
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}