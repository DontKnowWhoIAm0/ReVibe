pluginManagement {
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

rootProject.name = "ReVibe"
include(":app")
include(":core")
include(":core:design")
include(":core:navigation")
include(":core:db")
include(":core:ui")
include(":core:network")
include(":feature")
include(":feature:registration")
include(":feature:login")
include(":feature:catalog")
include(":feature:product_details")
include(":feature:search")
include(":feature:filters")
include(":core:domain")
include(":core:data")
include(":feature:favourites")
include(":feature:cart")
include(":feature:profile")
