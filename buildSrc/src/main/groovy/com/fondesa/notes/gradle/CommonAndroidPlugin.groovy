package com.fondesa.notes.gradle

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Shares the common configurations for an Android module.
 */
class CommonAndroidPlugin implements Plugin<Project> {

    @Override
    void apply(Project target) {
        target.with {
            apply from: "${target.rootDir.absolutePath}/android-config.gradle"
            apply plugin: 'kotlin-android'
            apply plugin: 'kotlin-kapt'
            apply plugin: 'kotlin-android-extensions'

            android {
                compileSdkVersion androidConfig.compileSdk
                defaultConfig {
                    minSdkVersion androidConfig.minSdk
                    targetSdkVersion androidConfig.targetSdk
                    multiDexEnabled true
                    vectorDrawables.useSupportLibrary = true

                    testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
                    testInstrumentationRunnerArguments clearPackageData: 'true'
                }

                compileOptions {
                    targetCompatibility JavaVersion.VERSION_1_8
                    sourceCompatibility JavaVersion.VERSION_1_8
                }

                testOptions {
                    execution 'ANDROIDX_TEST_ORCHESTRATOR'
                    animationsDisabled = true
                    unitTests.all {
                        testLogging {
                            events "passed", "skipped", "failed"
                        }
                    }
                }

                sourceSets.forEach {
                    it.java.srcDirs += "src/${it.name}/kotlin"
                }

                androidExtensions {
                    experimental = true
                }
            }

            kapt {
                useBuildCache = true
                // All the kapt arguments are ignored if they can't be handled by the single module.
                arguments {
                    // Arguments needed by Dagger.
                    arg("dagger.formatGeneratedSource", "disabled")
                    arg("dagger.gradle.incremental")
                }
            }

            dependencies {
                implementation deps.kotlinStdLib
                androidTestUtil deps.androidTestOrchestrator
            }
        }
    }
}