import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    id("java-library")
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.lombok)
    alias(libs.plugins.spring.boot.dependencies)
}


dependencies {
    api(platform(libs.mybatis.plus.bom))
    api(libs.commons.lang3)
    api(libs.guava)
    api(libs.jackson.databind)
    compileOnly(libs.spring.expression)
    compileOnly(libs.mybatis.plus.annotation)
}
