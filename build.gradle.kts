// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.jetbrains.kotlin.serialization) apply false
    alias(libs.plugins.detekt)
}

detekt {
    config.setFrom("$projectDir/config/detekt/detekt.yml")
    buildUponDefaultConfig = false
    source.setFrom(
        "app/src",
        "core/common/src",
        "core/data/api/src",
        "core/data/impl/src",
        "core/domain/src",
        "core/network/api/src",
        "core/network/impl/src",
        "core/ui/src",
        "feature/account/api/src",
        "feature/account/impl/src",
        "feature/articles/api/src",
        "feature/articles/impl/src",
        "feature/income/api/src",
        "feature/income/impl/src",
        "feature/outcome/api/src",
        "feature/outcome/impl/src",
        "feature/settings/api/src",
        "feature/settings/impl/src"
    )
}

tasks.register("detektAll") {
    group = "verification"
    description = "Run Detekt for all modules"
    dependsOn("detekt")
}
