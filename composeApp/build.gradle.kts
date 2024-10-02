import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)

}


val PropertiesFile = rootProject.file("composeApp/doubts.properties")
val properties = Properties()
properties.load(FileInputStream(PropertiesFile))

kotlin {

   /* js(IR) {
        browser {
            webpackTask {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
    }*//**/

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }



    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutines.swing)
                //  implementation(libs.ktor.client.okhttp)
            }
        }

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            api(libs.androidx.core.ktx.v1120)
            implementation(libs.bundles.ktor)
           //  implementation(libs.ktor.client.okhttp)

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.landscapist.coil3)
            implementation(libs.bundles.ktor)
            //implementation(libs.ktor.client.okhttp)
             implementation(libs.ktor.client.js)
        }
        nativeMain.dependencies {
            implementation(libs.bundles.ktor)
            implementation(libs.ktor.client.darwin)
        }


        jsMain.dependencies {
            implementation(libs.ktor.client.js)
            implementation(libs.bundles.ktor) // Include common Ktor dependencies
        }


    }
}

android {
    namespace = "com.infinitylearn.doubt"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.infinitylearn.doubt"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }

    flavorDimensions += "infinity"

    productFlavors {
        create("prod") {
            dimension = "infinity"
            buildConfigField(
                "String",
                "DOUBTS_BASE_URL",
                "\"${properties["DOUBTSAI_PREPROD_BASE_URL"]}\""
            )
        }

        create("preprod") {
            dimension = "infinity"
            buildConfigField(
                "String",
                "AIMENTOR_BASE_URL",
                "\"${properties["DOUBTSAI_PREPROD_BASE_URL"]}\""
            )
        }

        create("staging") {
            dimension = "infinity"
            buildConfigField(
                "String",
                "AIMENTOR_BASE_URL",
                "\"${properties["DOUBTSAI_PREPROD_BASE_URL"]}\""
            )
        }
    }

    dependencies {
        debugImplementation(compose.uiTooling)
    }
}


compose.desktop {
    application {
        mainClass = "com.infinitylearn.doubt.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.infinitylearn.doubt"
            packageVersion = "1.0.0"
        }
    }
}

compose {
    experimental {
        web.application {}
    }
}
