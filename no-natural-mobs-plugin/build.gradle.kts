plugins {
    id("java")
}

group = "me.prouddani"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.hytale.com/release")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    "compileOnly"("com.hypixel.hytale:Server:+")
    implementation("com.google.code.gson:gson:2.11.0")
}

tasks.test {
    useJUnitPlatform()
}