// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("com.android.library") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "2.2.0" apply false
//    id("com.huawei.agconnect")
}

buildscript{
    repositories {
        // Add the Maven address.
        maven ("https://developer.huawei.com/repo/")
    }
    dependencies {
        // Add dependencies.
        classpath("com.android.tools.build:gradle:8.1.1")
        classpath("com.huawei.agconnect:agcp:1.9.1.300")
    }
}
