plugins {
    id("java-library")
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.boot.dependencies)
}


dependencies {
    implementation(project(":modulix-framework-mybatis-plus:modulix-framework-mybatis-plus-api"))
    implementation(libs.sa.token.core)
}