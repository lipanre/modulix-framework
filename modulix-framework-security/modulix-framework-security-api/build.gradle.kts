plugins {
    id("java-library")
    alias(libs.plugins.lombok)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.boot.dependencies)
}


dependencies {
    implementation(libs.sa.token.core)
    implementation(libs.spring.boot.starter)
}