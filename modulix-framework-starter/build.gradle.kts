plugins {
    id("java-library")
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.lombok)
    alias(libs.plugins.spring.boot.dependencies)
}

dependencies {
    api(project(":modulix-framework-security:modulix-framework-security-satoken-starter"))
    api(project(":modulix-framework-web:modulix-framework-web-starter"))
}