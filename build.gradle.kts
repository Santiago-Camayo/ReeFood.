buildscript {
    repositories {
        google() // Necesario para encontrar el plugin de Google Services
        mavenCentral()
    }
    dependencies {
        // Esta es la dependencia que permite a Gradle descargar y usar el plugin de Google Services
        classpath("com.google.gms:google-services:4.4.2") // <-- Agrega esta línea
        // Si tu proyecto usa una dependencia de classpath para el plugin de Android Gradle, también iría aquí,
        // pero en proyectos nuevos con el bloque plugins, a veces se maneja de otra manera.
        // Lo crucial para Firebase es la línea de google-services.
    }
}

// Deja el bloque plugins que ya tienes después del bloque buildscript:
plugins {
    alias(libs.plugins.android.application) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}