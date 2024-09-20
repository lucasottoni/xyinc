plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "xyinc"

include("core")
include("repository")
include("api")
include("usecase")
