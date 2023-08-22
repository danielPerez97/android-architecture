plugins {
    alias(libs.plugins.sqldelight)
    kotlin("multiplatform")
}

sqldelight {
    databases {
        create("PetDb") {
            packageName.set("dev.danperez.petdb")
        }
    }
    linkSqlite.set(true)
}

kotlin {
    jvmToolchain(8)
    jvm()
}