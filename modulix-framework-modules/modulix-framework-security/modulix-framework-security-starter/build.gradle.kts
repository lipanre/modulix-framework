plugins {
    id("java-library")
    alias(libs.plugins.lombok)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.boot.dependencies)
}

dependencies {
    implementation(project(":modulix-framework-common:modulix-framework-common-core"))
    implementation(project(":modulix-framework-modules:modulix-framework-web:modulix-framework-web-api"))
    implementation(project(":modulix-framework-modules:modulix-framework-security:modulix-framework-security-api"))

    implementation(libs.jjwt)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.jakarta.servlet.api)
}
