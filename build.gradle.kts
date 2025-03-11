plugins {
    kotlin("jvm") version "2.1.10"
    id("me.champeau.jmh") version "0.7.2"
    id("io.morethan.jmhreport") version "0.9.6"
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:2.1.10"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation(kotlin("test"))
}

tasks {
    test {
        useJUnitPlatform()
    }
}

val jmhResultFile = project.layout.buildDirectory.file("results/jmh/results.json").get()
val jmhReportDir = project.layout.buildDirectory.dir("reports/jmh").get()

jmh {
    resultFormat = "json"
    resultsFile = jmhResultFile
}

jmhReport {
    jmhResultPath = jmhResultFile.toString()
    jmhReportOutput = jmhReportDir.toString()
}

val jmhExecutionTask = tasks.named("jmh")

tasks.named("jmhReport") {
    // Ensure JMH has been executed before producing the report, otherwise launch it
    inputs.files(jmhExecutionTask.get().outputs.files)
    // Workaround: Must explicitly create output folder, otherwise an error is raised
    doFirst {
        jmhReportDir.asFile.mkdirs()
    }
}
