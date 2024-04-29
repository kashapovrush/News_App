pluginManagement {
    repositories {
        google() {
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

rootProject.name = "News APP"
include(":app")
include(":core")
include(":core:api")
include(":features-mobile")
include(":features-mobile:features-headlines")
include(":core:utils")
include(":features-mobile:features-common")
include(":features-mobile:features-filter-news")
include(":features-mobile:palette")
include(":features-mobile:features-search")
include(":core:preferences")
include(":features-mobile:features-news-post")
include(":features-mobile:features-favourite")
include(":features-mobile:features-source")
include(":core:database")
include(":features-mobile:error")
include(":features-mobile:features-splash-screen")
