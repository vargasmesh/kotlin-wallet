import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("jvm") version "1.7.21"
    id("org.flywaydb.flyway") version "9.8.1"

    application
}

group = "local.vargasmesh"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val environment = System.getenv()
val ktorVersion = "2.1.3"
val KGraphQLVersion = "0.18.1"
val exposedVersion = "0.40.1"
val koinVersion = "3.2.2"

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.4.5")
    implementation("com.apurebase:kgraphql:$KGraphQLVersion")
    implementation("com.apurebase:kgraphql-ktor:$KGraphQLVersion")
    implementation("org.xerial:sqlite-jdbc:3.40.0.0")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.flywaydb:flyway-core:9.8.1")
    implementation("com.sksamuel.hoplite:hoplite-core:2.7.0")
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("org.javamoney:moneta:1.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
}

flyway {
    url = "jdbc:sqlite:${rootDir}/${environment["DATABASE__PATH"]}"
    locations = arrayOf("filesystem:src/main/resources/db/migration")
}