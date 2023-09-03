/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.2.1/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    checkstyle
    id("org.openrewrite.rewrite") version("6.2.4")

}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:31.1-jre")

    // Rewrite the code with checkstyle
    rewrite("org.openrewrite.recipe:rewrite-static-analysis:1.0.6")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    // Define the main class for the application.
    mainClass.set("chessboard.App")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

checkstyle {
    toolVersion = "10.12.3"
    maxWarnings = 0
    isIgnoreFailures = false
}


tasks.withType<Checkstyle>().configureEach {
    dependsOn(tasks.named("rewriteRun"))
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

rewrite {
    activeRecipe("org.openrewrite.staticanalysis.CodeCleanup")
}
