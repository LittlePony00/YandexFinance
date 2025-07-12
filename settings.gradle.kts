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
include(":core:data:api")
include(":core:data:impl")
include(":core:common")
include(":core:dagger")
include(":core:domain")
include(":core:network:api")
include(":core:network:impl")
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
include(":feature:transaction-edit:api")
include(":feature:transaction-edit:impl")
