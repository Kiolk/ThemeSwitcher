import org.jetbrains.dokka.gradle.DokkaTask
import java.util.Properties

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    id("org.jetbrains.dokka") version "1.9.20"
//    id("convention.publication")
    id("maven-publish")
    id("signing")
}

group = "com.github.kiolk.themeswitcher"
version = "1.0"

val dokkaOutputDir = file("$buildDir/dokka")

allprojects {
    apply(plugin = "org.jetbrains.dokka")
    val dokkaHtml by tasks.existing(DokkaTask::class)
    val dokkaJar by tasks.creating(org.gradle.jvm.tasks.Jar::class) {
        group = JavaBasePlugin.DOCUMENTATION_GROUP
        archiveClassifier.set("javadoc")
        from(dokkaHtml)
    }

//    val sourcesJar by tasks.creating(org.gradle.jvm.tasks.Jar::class) {
//        archiveClassifier.set("sources")
//        from(sourceSets.main.get().allSource)
//    }

    val pomArtifactId: String? by project
    if (pomArtifactId != null) {
        publishing {
            publications {
                publications.configureEach {
                    if(this is MavenPublication) {
                        artifact(dokkaJar)
                        pom {
//                            val pomDescription: String by project
//                            val pomUrl: String by project
//                            val pomName: String by project
//                            description.set(pomDescription)
//                            url.set(pomUrl)
//                            name.set(pomName)
                            name.set("theme-swithcer")
                            description.set("Theme switcher for multiplatform projects")
                            url.set("https://github.com/kiolk/ThemeSwitcher")
                        }
                    }
                }
//                create<MavenPublication>("maven") {
//                    val versionName: String by project
//                    val pomGroupId: String by project
//                    groupId = pomGroupId
//                    artifactId = pomArtifactId
//                    version = versionName
//                    from(components["java"])
//
//                    artifact(dokkaJar)
//                    artifact(sourcesJar)
//
//                    pom {
//                        val pomDescription: String by project
//                        val pomUrl: String by project
//                        val pomName: String by project
//                        description.set(pomDescription)
//                        url.set(pomUrl)
//                        name.set(pomName)
//                        scm {
//                            val pomScmUrl: String by project
//                            val pomScmConnection: String by project
//                            val pomScmDevConnection: String by project
//                            url.set(pomScmUrl)
//                            connection.set(pomScmConnection)
//                            developerConnection.set(pomScmDevConnection)
//                        }
//                        licenses {
//                            license {
//                                val pomLicenseName: String by project
//                                val pomLicenseUrl: String by project
//                                val pomLicenseDist: String by project
//                                name.set(pomLicenseName)
//                                url.set(pomLicenseUrl)
//                                distribution.set(pomLicenseDist)
//                            }
//                        }
//                        developers {
//                            developer {
//                                val pomDeveloperId: String by project
//                                val pomDeveloperName: String by project
//                                id.set(pomDeveloperId)
//                                name.set(pomDeveloperName)
//                            }
//                        }
//                    }
//                }
            }

            signing {
//                val file = File("maven-secret-key.asc")
//                val key = file.readText()
//                val properties = Properties()
//                properties.load(FileInputStream(file("local.properties")))
//                useGpgCmd()
//                useInMemoryPgpKeys(key, properties.getProperty("signing_password"))
//
//                sign(publishing.publications["maven"])
            }
            repositories {
                maven {
                    val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                    val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                    val versionName: String by project
                    url = if (versionName.endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
                }
            }
        }
    }
}



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
