
plugins {
    id("java-library")
    alias(libs.plugins.lombok)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.boot.dependencies)
}


dependencies {
    api(project(":modulix-framework-common"))
    implementation(platform(libs.mybatis.plus.bom))

    implementation(libs.jackson.databind)
    implementation(libs.spring.tx)
    implementation(libs.jakarta.annotation.api)
    implementation(libs.mybatis.plus.spring)
    implementation(libs.mybatis.plus.extension)
    implementation(libs.mybatis.plus.annotation)
    implementation(libs.mybatis.plus.core)
    implementation(libs.mybatis.plus.jsqlparser)
    implementation(libs.mybatis.plus.join.core)
    implementation(libs.mybatis.plus.join.extension)
    implementation(libs.mapstruct.plus)
}
