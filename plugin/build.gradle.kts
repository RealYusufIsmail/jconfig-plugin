plugins {
    `java-gradle-plugin`
    alias(libs.plugins.jvm)
    id("com.diffplug.spotless") version "6.25.0"
    id("com.gradle.plugin-publish") version "1.2.1"
    signing
}

version = "0.0.1.alpha1"
val releaseVersion by extra(!version.toString().endsWith("-SNAPSHOT"))
group = "io.github.realyusufismail"

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies { implementation("io.github.realyusufismail:jconfig:1.1.2") }

testing {
    suites {
        // Configure the built-in test suite
        val test by
            getting(JvmTestSuite::class) {
                // Use Kotlin Test test framework
                useKotlinTest("1.9.22")
            }

        // Create a new test suite
        val functionalTest by
            registering(JvmTestSuite::class) {
                // Use Kotlin Test test framework
                useKotlinTest("1.9.22")

                dependencies {
                    // functionalTest test suite depends on the production code in tests
                    implementation(project())
                }

                targets {
                    all {
                        // This test suite should run after the built-in test suite has run its
                        // tests
                        testTask.configure { shouldRunAfter(test) }
                    }
                }
            }
    }
}

gradlePlugin {
    // Define the plugin
    val jconfig by
        plugins.creating {
            id = "io.github.realyusufismail.jconfig-plugin" // Update the plugin ID
            implementationClass =
                "io.github.realyusufismail.JConfigPluginPlugin" // Update the implementation class
        }
}

gradlePlugin.testSourceSets.add(sourceSets["functionalTest"])

tasks.named<Task>("check") {
    // Include functionalTest as part of the check lifecycle
    dependsOn(testing.suites.named("functionalTest"))
}

spotless {
    kotlin {
        // Excludes build folder since it contains generated java classes.
        targetExclude("build/**")
        ktfmt("0.40").dropboxStyle()

        licenseHeader(
            """/*
 * Copyright 2024 Yusuf Arfan Ismail (RealYusufIsmail)
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ """)
    }

    kotlinGradle {
        target("**/*.gradle.kts")
        ktfmt("0.40").dropboxStyle()
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
}

gradlePlugin {
    website.set("https://github.com/RealYusufIsmail/jconfig-plugin")
    vcsUrl.set("https://github.com/RealYusufIsmail/jconfig-plugin.git")
    plugins {
        create("jconfig-plugin") {
            id = "io.github.realyusufismail.jconfig-plugin"
            implementationClass = "io.github.realyusufismail.JConfigPluginPlugin"
            displayName = "JConfig Plugin"
            description = "A simple plugin made to help with storing and retrieving data from a json file in gradle."
        }
    }
}

signing {
    afterEvaluate {
        // println "sign: " + isReleaseVersion
        val isRequired =
            releaseVersion &&
                    (tasks.withType<PublishToMavenRepository>().find { gradle.taskGraph.hasTask(it) } !=
                            null)
        setRequired(isRequired)
    }
}
