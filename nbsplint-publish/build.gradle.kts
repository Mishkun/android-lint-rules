plugins {
    id("com.android.library")
    `maven-publish`
    id("com.jfrog.bintray")
    id("com.github.b3er.local.properties")
}

val pomName = "Android nbsp lint"
val pomDesc = "Android lint rule to catch missing nbsp"
val pomUrl = "https://github.com/Mishkun/android-lint-rules"
val pomScmUrl = "https://github.com/Mishkun/android-lint-rules"
val pomIssuesUrl = "https://github.com/Mishkun/android-lint-rules/issues"
val pomDeveloperId = "mishkun"
val pomDeveloperName = "Mikhail Levchenko"

val pomLicenseName = "MIT"
val pomLicenseUrl = "https://opensource.org/licenses/mit-license.php"

val artifactVersion = "1.0.0"
val group = "io.github.mishkun"
val artifact = "nbsp-android-lint"

operator fun <T> Property<T>.plusAssign(value: T) = set(value)

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("nbsp-lint") {
            artifact("$buildDir/outputs/aar/nbsplint-publish-release.aar")

            groupId = group
            artifactId = artifact
            version = artifactVersion

            pom {
                name += pomName
                description += pomDesc
                url += pomUrl
                licenses {
                    name += pomLicenseName
                    url += pomLicenseUrl
                }
                developers {
                    developer {
                        id += pomDeveloperId
                        name += pomDeveloperName
                    }
                }
                issueManagement {
                    url += pomIssuesUrl
                }
            }
        }
    }
}

configure<com.jfrog.bintray.gradle.BintrayExtension> {
    user = project.findProperty("bintray.user").toString()
    key = project.findProperty("bintray.apiKey").toString()
    publish = true

    setPublications("nbsp-lint")

    pkg.apply {
        repo = "mAndroidLint-rules"
        name = "nbsp-lint"
        user = "mishkun"
        userOrg = pomDeveloperId
        vcsUrl = pomScmUrl
        description = pomDesc
        setLicenses(pomLicenseName)
        websiteUrl = pomUrl
        issueTrackerUrl = pomIssuesUrl

        version.apply {
            name = artifactVersion
            desc = pomDesc
            vcsTag = artifactVersion
        }
    }
}


android {
    compileSdkVersion(Sdk.COMPILE_SDK_VERSION)

    defaultConfig {
        minSdkVersion(Sdk.MIN_SDK_VERSION)
    }

    libraryVariants.all {
        generateBuildConfig?.enabled = false
    }
}

dependencies {
    lintPublish(project(":nbsplint"))
}
