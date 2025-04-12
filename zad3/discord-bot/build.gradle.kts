plugins {
    kotlin("jvm") version "1.8.0"
    application
    kotlin("plugin.serialization") version "1.8.0"
    id("io.ktor.plugin") version "2.3.0"
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

kotlin {
    jvmToolchain(17)
}

dependencies {

        implementation("io.ktor:ktor-server-content-negotiation:2.3.0")
        implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("io.ktor:ktor-server-core:2.3.0")
    implementation("io.ktor:ktor-server-netty:2.3.0")
    implementation("dev.kord:kord-core:0.11.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")


    // Ktor dependencies
    implementation("io.ktor:ktor-server-core:2.3.0")
    implementation("io.ktor:ktor-server-netty:2.3.0")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")


    implementation("dev.kord:kord-core:0.9.0")


    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")


    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")


    implementation("ch.qos.logback:logback-classic:1.4.5")

    // Testing
    testImplementation("io.ktor:ktor-server-tests:2.3.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.0")
}

application {
    mainClass.set("com.jw.MainKt")
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Main-Class"] = "com.jw.MainKt"
    }

    from(
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    )
}

tasks.test {
    useJUnitPlatform()
}
