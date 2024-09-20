plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(project(":core"))

    implementation("org.springframework:spring-tx")

    testImplementation(kotlin("test"))
    // Dependências específicas do módulo
}