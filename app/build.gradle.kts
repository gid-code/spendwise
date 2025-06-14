plugins {
   alias(libs.plugins.android.application)
   alias(libs.plugins.jetbrains.kotlin.android)
   id("kotlin-kapt")
   alias(libs.plugins.dagger.hilt.android)
   alias(libs.plugins.kotlin.plugin.serialization)
}

android {
   namespace = "com.gidcode.spendwise"
   compileSdk = 34

   defaultConfig {
      applicationId = "com.gidcode.spendwise"
      minSdk = 26
      targetSdk = 34
      versionCode = 1
      versionName = "1.0"

//      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
      testInstrumentationRunner = "com.gidcode.spendwise.HiltTestRunner"
      vectorDrawables {
         useSupportLibrary = true
      }
   }

   sourceSets {
//      androidTest {
//
//      }
//      androidTest {
//         assets.srcDirs = ["src/debug/assets"]
//      }
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
      compose = true
   }
   testOptions {
      unitTests {
         isIncludeAndroidResources = true
      }
   }
   composeOptions {
      kotlinCompilerExtensionVersion = "1.5.12"
   }
   packaging {
      resources {
         excludes += "/META-INF/{AL2.0,LGPL2.1}"
      }
   }
}

dependencies {

   implementation(libs.androidx.appcompat)
   implementation(libs.androidx.core.ktx)
   implementation(libs.androidx.lifecycle.runtime.ktx)
   implementation(libs.androidx.activity.compose)
   implementation(platform(libs.androidx.compose.bom))
   implementation(libs.androidx.ui)
   implementation(libs.androidx.ui.graphics)
   implementation(libs.androidx.ui.tooling.preview)
   implementation(libs.androidx.material3)
   implementation(libs.androidx.ui.text.google.fonts)
   implementation(libs.androidx.navigation.runtime.ktx)
   implementation(libs.androidx.navigation.compose)
   implementation(libs.androidx.material.icons.extended)

   //Retrofit
   implementation(libs.logging.interceptor)
   implementation(libs.retrofit)
   implementation(libs.converter.gson)

   // Hilt - dependency injection
   implementation (libs.hilt.android)
   implementation(libs.androidx.monitor)
   testImplementation(libs.junit.jupiter)
//   androidTestImplementation(libs.junit.jupiter)
   kapt (libs.hilt.compiler)
   kapt (libs.androidx.hilt.compiler)
   androidTestImplementation (libs.hilt.android.testing)
   kaptAndroidTest (libs.hilt.android.compiler)

   // Preferences DataStore
   implementation(libs.androidx.datastore.preferences)

   // Compose Accompanist
   implementation (libs.accompanist.insets)

   // Kotlin serialization
   implementation (libs.kotlinx.serialization.json)

   // Biometric
   implementation(libs.androidx.biometric)

   // Testing Dependencies
   testImplementation (libs.truth)
   testImplementation (libs.mockito.core)
   testImplementation (libs.robolectric)
   androidTestImplementation (libs.truth)
   androidTestImplementation (libs.androidx.core.testing)

   testImplementation(libs.mockwebserver)
   androidTestImplementation(libs.mockwebserver)

   testImplementation(libs.junit)
   androidTestImplementation(libs.androidx.junit)
   androidTestImplementation(libs.androidx.espresso.core)
   androidTestImplementation(platform(libs.androidx.compose.bom))
   androidTestImplementation(libs.androidx.ui.test.junit4)
   debugImplementation(libs.androidx.ui.tooling)
   debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
   correctErrorTypes = true
}
