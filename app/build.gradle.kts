plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

buildscript {
    repositories {
        google()  // 确保添加了 Google Maven 仓库
        mavenCentral()  // Maven Central 可选
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.0")
    }
}



android {

    namespace = "com.example1.activity"
    compileSdk = 34

    sourceSets {
        getByName("main") {
            java.srcDir("src/main/java")
            res.srcDir("src/main/res")
            manifest.srcFile("src/main/AndroidManifest.xml")
        }
    }
    defaultConfig {
        applicationId = "com.example1.activity"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    /*sourceSets {
        main {
            jniLibs.srcDirs = ["libs"]
        }
    }*/
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(files("libs\\jackson-annotations-2.8.1.jar"))
    implementation(files("libs\\jackson-core-2.8.1.jar"))
    implementation(files("libs\\jackson-databind-2.8.1.jar"))
    implementation(files("libs\\gson-2.7.jar"))
    implementation("androidx.core:core-ktx:+")
    implementation("androidx.core:core-ktx:+")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}

tasks.register("testSourceSet") {
    doLast {
        println(android.sourceSets.getByName("main").java.srcDirs)
    }
}
