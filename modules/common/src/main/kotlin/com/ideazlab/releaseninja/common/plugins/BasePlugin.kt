package com.ideazlab.releaseninja.common.plugins

interface BasePlugin {
    fun getName(): String
    fun enabled(): Boolean = false
    fun getModalId(): String
    fun getDisplayName(): String

}