plugins {
    id("java-library")
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.lombok)
    alias(libs.plugins.spring.boot.dependencies)

}

dependencies {
    implementation(project(":modulix-framework-modules:modulix-framework-mybatis-plus:modulix-framework-mybatis-plus-api"))
    implementation(libs.spring.security.core)
}
