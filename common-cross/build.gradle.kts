import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.xaldigital.common_cross"
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
            artifactId = "common-cross"
            version = "2.8.5"

            artifact("${layout.buildDirectory.get().asFile}/outputs/aar/com_menta_android_common_cross_common-cross_2.8.5_common-cross-2.8.5.aar")

            pom {
                name = "Common Cross"
                description = "Dependencia para el intercambio interno de componentes."
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