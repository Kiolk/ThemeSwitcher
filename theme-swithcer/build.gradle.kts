plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    id("org.jetbrains.dokka") version "1.9.20"
    id("convention.publication")
}

group = "com.github.kiolk"
version = "1.0.0"
val dokkaOutputDir = file("$buildDir/dokka")

kotlin {
    jvmToolchain(11)
    androidTarget {
        publishLibraryVariants("release")
    }

    jvm()

    js {
        browser {
            webpackTask {
                mainOutputFileName = "theme-swithcer.js"
            }
        }
        binaries.executable()
    }

    wasmJs {
        browser()
        binaries.executable()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "theme-swithcer"
            isStatic = true
        }
    }

    listOf(
        macosX64(),
        macosArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "theme-swithcer"
            isStatic = true
        }
    }

    linuxX64 {
        binaries.staticLib {
            baseName = "theme-swithcer"
        }
    }


    mingwX64 {
        binaries.staticLib {
            baseName = "theme-swithcer"
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.multiplatformSettings)
            implementation(libs.multiplatformSettings.coroutines)
//            implementation(libs.koin.core)
//            implementation(libs.koin.compose)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
        }

        jvmMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
        }

    }

    //https://kotlinlang.org/docs/native-objc-interop.html#export-of-kdoc-comments-to-generated-objective-c-headers
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        compilations["main"].compilerOptions.options.freeCompilerArgs.add("-Xexport-kdoc")
    }

}

android {
    namespace = "com.github.kiolk.themeswitcher"
    compileSdk = 35

    defaultConfig {
        minSdk = 21
    }
}
