package com.fondesa.notes.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Shares the common configurations for a JVM-only module.
 */
class CommonJvmPlugin implements Plugin<Project> {

    @Override
    void apply(Project target) {
        target.with {
            apply plugin: 'kotlin'
            apply plugin: 'kotlin-kapt'

            kapt {
                useBuildCache = true
                // All the kapt arguments are ignored if they can't be handled by the single module.
                arguments {
                    // Arguments needed by Dagger.
                    arg("dagger.formatGeneratedSource", "disabled")
                    arg("dagger.gradle.incremental")
                }
            }

            sourceSets.main.java.srcDir "$buildDir/generated/source/kaptKotlin/main"

            test {
                testLogging {
                    events "passed", "skipped", "failed"
                }
            }

            dependencies {
                implementation deps.kotlinStdLib
            }
        }
    }
}