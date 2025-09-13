[![](https://jitpack.io/v/cristmasbox/MAAT.svg)](https://jitpack.io/#cristmasbox/MAAT)
# Mobile API for Ancient Texts (MAAT)
A library for rendering egyptian hieroglyphic texts by using the `GlyphX` format.

## Usage
### Implemetation with jitpack
Add this to your `settings.gradle.kts` at the end of repositories:
```
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    mavenCentral()
      maven { url = uri("https://jitpack.io") }
    }
}
```
Then add this dependency to your `build.gradle.kts` file:
```
dependencies {
  implementation("com.github.cristmasbox:MAAT:1.0.0")
}
```
> [!NOTE]
> For the implementation for other build systems like `Groovy` see [here](https://jitpack.io/#cristmasbox/MAAT/)

### Implementation with `.aar` file
Download the `MAAT_release_versionname.aar` file from this repository, create a `libs` folder in your project directory and paste the file there. Then add this dependency to your `build.gradle.kts` file:
```
dependencies {
  implementation(files("../libs/MAAT_release_versionname.aar"))
}
```
> [!NOTE]
> I recommend using the `release` file instead of the `debug` file

> [!IMPORTANT]
> If you renamed the `.aar` file you also have to change the name in the dependencies

## Version Catalog
### 13.09.2025@1.0.0
This is the first release of the MAAT library.
### latest Version
`13.09.2025@1.0.0`
