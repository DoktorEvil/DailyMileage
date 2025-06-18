// build.gradle.kts (Project level)
plugins {
    // ... other plugins
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false
    alias(libs.plugins.kotlin.compose) apply false
}