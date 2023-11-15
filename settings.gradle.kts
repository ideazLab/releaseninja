rootProject.name = "releaseninja"

include("artifactory", "common", "github", "nexus", "web")

project(":common").projectDir = file("./modules/common")
project(":artifactory").projectDir = file("./modules/amt/artifactory")
project(":nexus").projectDir = file("./modules/amt/nexus")
project(":github").projectDir = file("./modules/scm/github")