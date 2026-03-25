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
include(":feature")
include(":feature:registration")
include(":feature:login")
include(":feature:catalog")
