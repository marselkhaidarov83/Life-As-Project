pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://artifactory-external.vkpartner.ru/artifactory/maven")
        maven("https://jitpack.io")
        maven ("https://developer.huawei.com/repo/")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        jcenter()
        maven("https://artifactory-external.vkpartner.ru/artifactory/maven")
        maven("https://jitpack.io")
        maven ("https://developer.huawei.com/repo/")
    }
}

rootProject.name = "Life As Project"
include(":app")
