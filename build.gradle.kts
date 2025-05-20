buildscript {
    repositories {
        google() // Necesario para encontrar el plugin de Google Services
        mavenCentral()
    }
    dependencies {

    }
}

// Deja el bloque plugins que ya tienes después del bloque buildscript:
plugins {
    alias(libs.plugins.android.application) apply false
    id("com.google.gms.google-services") version "4.4.1" apply false // Añade esto
}