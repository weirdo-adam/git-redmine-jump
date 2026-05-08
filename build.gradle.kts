plugins {
    id("java")
    id("org.jetbrains.intellij.platform")
}

group = "com.pengfei.li"
version = "2026.2.0"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdea("2023.3.7")
        bundledPlugins("Git4Idea")
    }
}

intellijPlatform {
    pluginConfiguration {
        name = "Git Redmine Jump"
    }

    signing {
        certificateChain = providers.environmentVariable("CERTIFICATE_CHAIN")
        privateKey = providers.environmentVariable("PRIVATE_KEY")
        password = providers.environmentVariable("PRIVATE_KEY_PASSWORD")
    }

    publishing {
        token = providers.environmentVariable("PUBLISH_TOKEN")
    }
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    patchPluginXml {
        sinceBuild.set("233")
        untilBuild.set("271.*")
    }
}
