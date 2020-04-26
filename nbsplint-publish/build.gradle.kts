plugins {
    id("com.android.library")
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
