plugins {
    id("java-library")
    alias(libs.plugins.lombok)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.boot.dependencies)
}

dependencies {
    api(project(":modulix-framework-security:modulix-framework-security-api"))
    implementation(libs.spring.boot.starter.web)
    implementation(libs.sa.token.jwt)
    implementation(libs.sa.token.temp.jwt)
    implementation(libs.sa.token.starter)
    annotationProcessor(libs.spring.boot.configuration.processor)
}
