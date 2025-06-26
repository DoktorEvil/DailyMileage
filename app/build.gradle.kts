plugins {
    alias(libs.plugins.android.application) // Use alias from version catalog
    alias(libs.plugins.kotlin.android)      // Use alias from version catalog
    alias(libs.plugins.kotlin.compose)      // Use alias from version catalog
    alias(libs.plugins.kotlin.serialization) // Use alias from version catalog
}

android {
    namespace = "com.example.dailymileageapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.dailymileageapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    composeOptions {
        // This should ideally be managed by your Compose Compiler plugin version in libs.versions.toml
        // which is linked to your Kotlin version.
        // If your libs.plugins.kotlin.compose correctly sets this, you might not need this line.
        // However, if you do set it, ensure it MATCHES the version compatible with your Kotlin version.
        // For Kotlin 2.0.21, composeCompiler = "1.5.14" is a common pairing.
        // For Kotlin 1.9.22, composeCompiler = "1.5.8" or "1.5.10" etc.
        // Let's assume your libs.versions.toml has a composeCompiler version set.
        // If not, you need to add it there and then reference it.
        // For now, I will comment it out, assuming your 'kotlin-compose' plugin from the catalog handles it.
        // kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get() // Example if 'composeCompiler' is in your TOML
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/INDEX.LIST" // Often good to add
            excludes += "/META-INF/DEPENDENCIES" // Often good to add
        }
    }
}

dependencies {
    // Use the Compose BOM defined in your libs.versions.toml
    // Assuming your libs.versions.toml has:
    // [versions]
    // composeBom = "2024.09.00" (or the latest stable one)
    // [libraries]
    // androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Activity Compose - use version from libs.versions.toml
    // Assuming:
    // [versions]
    // activityCompose = "1.10.1" (or latest stable)
    // [libraries]
    // androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
    implementation(libs.androidx.activity.compose)

    // Core KTX - use version from libs.versions.toml
    // Assuming:
    // [versions]
    // coreKtx = "1.16.0" (or latest stable)
    // [libraries]
    // androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
    implementation(libs.androidx.core.ktx)

    // Lifecycle Runtime KTX - use version from libs.versions.toml
    // Assuming:
    // [versions]
    // lifecycleRuntimeKtx = "2.8.7" (or latest stable)
    // [libraries]
    // androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycleRuntimeKtx" }
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // You might also need lifecycle-viewmodel-compose if you plan to use ViewModels with Compose
    // implementation(libs.androidx.lifecycle.viewmodel.compose) // Example, add to TOML if needed

    // DataStore Preferences - use version from libs.versions.toml
    // Assuming:
    // [versions]
    // datastorePreferences = "1.1.1" (or latest stable, 1.1.1 is fairly recent and stable)
    // [libraries]
    // androidx-datastore-preferences = { group = "androidx.datastore", name = "datastore-preferences", version.ref = "datastorePreferences" }
    implementation(libs.androidx.datastore.preferences) // Ensure this alias is in your libs.versions.toml

    // KotlinX Serialization JSON - use version from libs.versions.toml
    // Assuming:
    // [versions]
    // kotlinxSerializationJson = "1.7.3" // (or latest compatible with your Kotlin version)
    // [libraries]
    // kotlinx-serialization-json = { group = "org.jetbrains.kotlinx", name = "kotlinx-serialization-json", version.ref = "kotlinxSerializationJson" }
    implementation(libs.kotlinx.serialization.json)

    // Testing dependencies - use versions from libs.versions.toml
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom)) // Use the same BOM
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}