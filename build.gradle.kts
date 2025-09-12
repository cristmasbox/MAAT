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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

mavenPublishing {
  publishToMavenCentral()
  signAllPublications()

  //coordinates("com.blueapps.maat", "maat")

  pom {
    name.set("Mobile API for Ancient Texts (MAAT)")
    description.set("Library for rendering egyptian hieroglyphic texts.")
    inceptionYear.set("2025")
    url.set("https://github.com/cristmasbox/MAAT")
    licenses {
      license {
        name.set("GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007")
        url.set("https://www.gnu.org/licenses/")
        distribution.set("https://www.gnu.org/licenses/")
      }
    }
    developers {
      developer {
        id.set("cristmasbox")
        name.set("Georg Schierholt")
        url.set("https://github.com/cristmasbox")
      }
    }
    scm {
      url.set("https://github.com/cristmasbox/MAAT")
      connection.set("scm:git:git://github.com/cristmasbox/MAAT.git")
      developerConnection.set("scm:git:ssh://git@github.com/cristmasbox/MAAT.git")
    }
  }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.13.0")
    testImplementation("junit:junit4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")

}





