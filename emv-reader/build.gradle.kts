import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.xaldigital.emv_reader"
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
            artifactId = "emv-reader"
            version = "1.6.4"

            artifact("${layout.buildDirectory.get().asFile}/outputs/aar/com_menta_android_emv_i9100_reader_reader_1.6.4_reader-1.6.4.aar")

            pom {
                name = "EMV Reader"
                description = "Dependencia para procesar la lectura de tarjetas."
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