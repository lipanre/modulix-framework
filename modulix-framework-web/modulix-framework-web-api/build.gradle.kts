plugins {
    id("java-library")
    alias(libs.plugins.lombok)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.boot.dependencies)
}


dependencies {
    api(project(":modulix-framework-common"))

    implementation(libs.logback.classic)
    implementation(libs.jakarta.servlet.api)
    implementation(libs.spring.web)
}
