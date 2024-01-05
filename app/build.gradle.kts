import com.android.sdklib.AndroidVersion.VersionCodes

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.dokka") version "1.9.10"
    kotlin("kapt")
}

android {
    namespace = "com.project.foodtracker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.project.foodtracker"
        minSdk = 28
        targetSdk = VersionCodes.TIRAMISU
        versionCode = 1
        versionName = "1.0"

        //testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.project.foodtracker.CustomTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true"
                )
            }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.6")
    implementation("androidx.test:core-ktx:1.5.0")
    implementation("androidx.media3:media3-test-utils:1.2.0")
    implementation("com.google.android.datatransport:transport-runtime:3.2.0")
    implementation("com.google.android.engage:engage-core:1.3.1")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("org.mockito:mockito-android:5.7.0")
    implementation("androidx.test:runner:1.5.2")
    implementation("com.google.ar:core:1.41.0")
    testImplementation("junit:junit:4.13.2")
    implementation("io.coil-kt:coil:2.5.0")
    implementation("io.coil-kt:coil-compose:2.5.0")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.11.0")
    androidTestImplementation("androidx.navigation:navigation-testing:2.7.6")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.navigation:navigation-compose:2.7.6")

    // Room Database dependencies
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-rxjava2:$room_version")
    implementation("androidx.room:room-rxjava3:$room_version")
    implementation("androidx.room:room-guava:$room_version")
    testImplementation("androidx.room:room-testing:$room_version")
    implementation("androidx.room:room-paging:$room_version")

    // Hilt for dependency injection
    val hilt_version = "2.48.1"
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hilt_version")
    kaptAndroidTest("com.google.dagger:hilt-compiler:$hilt_version")
    testImplementation("com.google.dagger:hilt-android-testing:$hilt_version")
    kaptTest("com.google.dagger:hilt-compiler:$hilt_version")

    implementation("androidx.hilt:hilt-navigation-fragment:1.1.0")
    implementation("androidx.hilt:hilt-work:1.1.0")
    kapt("androidx.hilt:hilt-compiler:1.1.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // RetroFit for Api calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Timber for logging
    implementation("com.jakewharton.timber:timber:5.0.1")

}

kapt {
    correctErrorTypes = true
}


subprojects {
    apply(plugin = "org.jetbrains.dokka")
}

tasks.dokkaHtml.configure {
    outputDirectory.set(file("dokka"))
}

