pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "YandexFinance"
include(":app")
include(":core:ui")
include(":core:data")
include(":core:common")
include(":core:domain")
include(":core:network")
include(":feature:income:api")
include(":feature:income:impl")
include(":feature:outcome:api")
include(":feature:outcome:impl")
include(":feature:account:api")
include(":feature:account:impl")
include(":feature:settings:api")
include(":feature:settings:impl")
include(":feature:articles:api")
include(":feature:articles:impl")
