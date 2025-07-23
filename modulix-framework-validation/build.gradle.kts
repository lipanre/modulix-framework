plugins {
    id("java-library")
    alias(libs.plugins.lombok)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.boot.dependencies)
}
dependencies {
    api(libs.spring.boot.starter.validation)
}
