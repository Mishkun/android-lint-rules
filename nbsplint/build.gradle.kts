plugins {
    `java-library`
    kotlin("jvm") version "1.3.72"
}

sourceSets {
    val main by getting
    main.java.srcDirs("src/main/java")

    val test by getting
    test.java.srcDirs("src/test/java")
}


tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.jar {
    manifest {
        attributes["Lint-Registry-v2"] = "io.github.mishkun.nbsplint.NbspRegistry"
    }
}

dependencies {
    val lintVersion = "26.5.0"
    val kotestVersion = "4.0.5"
    compileOnly(kotlin("stdlib-jdk8"))
    compileOnly("com.android.tools.lint:lint-api:$lintVersion")
    compileOnly("com.android.tools.lint:lint-checks:$lintVersion")
    testImplementation("com.android.tools.lint:lint:$lintVersion")
    testImplementation("com.android.tools.lint:lint-tests:$lintVersion")
    testImplementation("com.android.tools:testutils:$lintVersion")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-property-jvm:$kotestVersion")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
