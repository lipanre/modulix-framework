plugins {
    id("java-library")
    alias(libs.plugins.lombok)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.boot.dependencies)
}


dependencies {
    implementation(project(":modulix-framework-mybatis-plus:modulix-framework-mybatis-plus-api"))
    implementation(libs.sa.token.core)
    implementation(libs.ttl)
    implementation(libs.spring.boot.starter)
}