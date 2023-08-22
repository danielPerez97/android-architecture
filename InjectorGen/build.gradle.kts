plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {

    // Anvil Compiler API
    api(libs.anvil.compiler.api)
    implementation(libs.anvil.compiler.utils)

    // Auto Service
    implementation(libs.ksp.api)
    implementation(libs.daggerApi)

    implementation(project(":scopes"))

    // Optional:
    compileOnly(libs.autoservice.annotations)
    kapt(libs.autoservice)
}