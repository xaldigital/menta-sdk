import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.xaldigital.urovo_libs"
    compileSdk = 34

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }

    defaultConfig {
        minSdk = 27

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

val properties = Properties().apply {
    load(file("../github.properties").inputStream())
}

val userName: String = properties.getProperty("USERNAME") ?: ""
val tokenKey: String = properties.getProperty("TOKEN_KEY") ?: ""

publishing {
    publications {
        create<MavenPublication>("github") {

            groupId = "com.xaldigital"
            artifactId = "urovo-libs"
            version = "7.8.0"

            artifact("${layout.buildDirectory.get().asFile}/outputs/aar/urovosdkLibs_v78.aar")

            pom {
                name = "Urovo Libs"
                description = "Dependencia del fabricante de la terminal."
                url = "https://github.com/xaldigital/menta-sdk.git"
            }
        }
    }

    repositories {
        maven {

            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/xaldigital/menta-sdk")
            credentials {
                this.username = userName
                this.password = tokenKey
            }
        }

    }
}