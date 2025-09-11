import kotlin.math.sign

plugins {
    id("com.android.library") version "8.13.0"
    id("maven-publish")
    id("signing")
    //id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

android {
    namespace = "com.blueapps.maat"
    compileSdk = 36

    defaultConfig {

        minSdk = 16
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "com.blueapps"
                artifactId = "maat"
                version = "1.0.0"

                pom {
                    name.set("Mobile API for Ancient Texts (MAAT)")
                    description.set("Library for rendering egyptian hieroglyphic texts.")
                    url.set("https://github.com/cristmasbox/MAAT")
                    licenses {
                        license {
                            name.set("GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007")
                            url.set("https://www.gnu.org/licenses/")
                        }
                    }
                    developers {
                        developer {
                            id.set("cristmasbox")
                            name.set("Georg Schierholt")
                            email.set("sonne.zucker@gmx.de")
                        }
                    }
                    scm {
                        url.set("https://github.com/cristmasbox/MAAT")
                    }
                }
            }
        }
    }

    signing {
        sign(publishing.publications["release"])
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.13.0")
    testImplementation("junit:junit4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
}