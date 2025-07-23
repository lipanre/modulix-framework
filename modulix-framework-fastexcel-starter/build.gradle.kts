plugins {
    id("java-library")
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.boot.dependencies)
}

dependencies {
    api(project(":modulix-framework-common"))
    api(libs.fastexcel)
    compileOnlyApi(libs.spring.boot.starter.web)
}
