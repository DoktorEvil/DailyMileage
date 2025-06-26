// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Reference the Android Application plugin alias from the version catalog
    alias(libs.plugins.android.application) apply false

    // Reference the Kotlin Android plugin alias from the version catalog
    alias(libs.plugins.kotlin.android) apply false

    // Declare the Kotlin Compose plugin alias from the version catalog, making it available to modules
    alias(libs.plugins.kotlin.compose) apply false
    // This is to assist in enabling serialization to persist the data in the app
    alias(libs.plugins.kotlin.serialization) apply false

// If you have library modules in your project (besides ':app'),
// you would also need to define 'com.android.library' in your libs.versions.toml
// and add its alias here. For example:
// alias(libs.plugins.android.library) apply false // Example if you had a library module
} // Make sure this closing brace for the plugins block is present

// Any other configurations for the root project can go here