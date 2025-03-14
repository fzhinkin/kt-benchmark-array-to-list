import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.benchmark)
}

repositories {
    mavenCentral()
}

kotlin {
    targets {
        jvm {
            compilerOptions {
                jvmTarget = JvmTarget.JVM_21
            }
        }

        linuxX64()

        js {
            nodejs()
        }

        wasmJs {
            nodejs()
        }
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain {
            kotlin.srcDir("src/main/kotlin")
        }
        commonTest {
            kotlin.srcDir("src/test/kotlin")
        }
    }

    val benchmarkSourceSet = sourceSets.create("benchmark") {
        kotlin.srcDir("src/benchmark/kotlin")
        dependencies {
            implementation(libs.kotlinx.benchmark)
        }
    }

    targets.matching { it.name != "metadata"}.all {
        compilations.create(benchmarkSourceSet.name) {
            associateWith(this@all.compilations.getByName("main"))
            defaultSourceSet {
                dependencies {
                    implementation(libs.kotlinx.benchmark)
                }
                dependsOn(benchmarkSourceSet)
            }
        }
    }
}

dependencies {
    commonMainImplementation(libs.kotlinx.benchmark)
    commonTestImplementation(libs.kotlin.test)
}

benchmark {
    targets {
        register("jvmBenchmark")
        register("linuxX64Benchmark")
        register("jsBenchmark")
        register("wasmJsBenchmark")
    }
}
