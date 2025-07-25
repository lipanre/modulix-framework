plugins {
    id("java-library")
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.lombok)
    alias(libs.plugins.spring.boot.dependencies)

}

dependencies {
    api(project(":modulix-framework-mybatis-plus:modulix-framework-mybatis-plus-api"))
    api(libs.spring.security.core)
    api(libs.ttl)
}
