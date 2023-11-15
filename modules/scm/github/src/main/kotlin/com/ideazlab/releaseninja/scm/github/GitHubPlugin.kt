package com.ideazlab.releaseninja.scm.github

import com.ideazlab.releaseninja.common.plugins.scm.SCMPlugin
import jakarta.inject.Singleton

@Singleton
class GitHubPlugin : SCMPlugin {
    override fun getName() = "Github"

    override fun enabled() = false
    override fun getModalId(): String {
        TODO("Not yet implemented")
    }

    override fun getDisplayName(): String {
        TODO("Not yet implemented")
    }
}