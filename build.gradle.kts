plugins {
    id("com.android.library") version "8.13.0"
    id("com.vanniktech.maven.publish") version "0.34.0"
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

    publishing {
        singleVariant("release") {
            // if you don't want sources/javadoc, remove these lines
            withSourcesJar()
            withJavadocJar()
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }


}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.7.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates("com.blueapps", "maat", "1.0.0")

    pom {
        name = "Maat library"
        description = "Library for rendering egyptian hieroglyphic texts."
        inceptionYear = "2025"
        url = "https://github.com/cristmasbox/MAAT"
        licenses {
            license {
                name = "GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007"
                url = "https://www.gnu.org/licenses/"
            }
        }
        developers {
            developer {
                id = "cristmasbox"
                name = "Georg Schierholt"
                url = "https://github.com/cristmasbox"
            }
        }
        scm {
            url = "https://github.com/cristmasbox/MAAT"
            connection = "scm:git:git://github.com/cristmasbox/MAAT"
            developerConnection = "scm:git:ssh://git@github.com/cristmasbox/MAAT"
        }
    }
}