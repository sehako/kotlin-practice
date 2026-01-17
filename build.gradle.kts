import java.io.File

plugins {
    kotlin("jvm") version "2.2.21"
    application
}

group = "com.sehako"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("src")
    }
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}

afterEvaluate {
    val mainSourceSet = sourceSets.getByName("main")
    mainSourceSet.kotlin.srcDirs.forEach { srcDir: File ->
        srcDir.walk().forEach { file: File ->
            if (file.isFile && file.extension == "kt" && file.readText().contains("fun main")) {
                val relativePath = file.relativeTo(srcDir)
                val className = relativePath.path.removeSuffix(".kt").replace(File.separator, ".") + "Kt"
                val taskName = "run_" + relativePath.path.removeSuffix(".kt").replace(File.separator, "_").replace('.', '_')

                if (tasks.findByName(taskName) == null) {
                    tasks.register<JavaExec>(taskName) {
                        group = "Application"
                        description = "Run ${file.name}"
                        classpath = mainSourceSet.runtimeClasspath
                        mainClass.set(className)
                        standardInput = System.`in`
                    }
                }
            }
        }
    }
}
