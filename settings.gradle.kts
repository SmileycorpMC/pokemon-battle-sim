
plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

dependencies {
  implementation 'com.google.code.gson:gson:2.10.1'
}

rootProject.name = "pokemon-battle-sim"
include("lib")
