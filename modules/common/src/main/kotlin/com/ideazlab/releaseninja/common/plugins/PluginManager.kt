package com.ideazlab.releaseninja.common.plugins

import com.ideazlab.releaseninja.common.plugins.amt.AMTDataDto
import com.ideazlab.releaseninja.common.plugins.amt.AMTPlugin
import com.ideazlab.releaseninja.common.plugins.scm.SCMPlugin
import io.micronaut.context.ApplicationContext
import io.micronaut.kotlin.context.getBeansOfType
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class PluginManager(
    private val applicationContext: ApplicationContext
) {
    private var amtPlugins: List<AMTPlugin> = emptyList()
    private var scmPlugins: List<SCMPlugin> = ArrayList()
    private val logger: Logger = LoggerFactory.getLogger(PluginManager::class.java)

    init {
        logger.debug("")
        amtPlugins = applicationContext.getBeansOfType<AMTPlugin>() as List<AMTPlugin>
        scmPlugins = applicationContext.getBeansOfType<SCMPlugin>() as List<SCMPlugin>
        logger.info("amt plugin count ${amtPlugins.count().toString()}")
        logger.info("scm plugin count ${scmPlugins.count().toString()}")
    }

    fun getAMTPlugins(enabled: Boolean) = run {
        amtPlugins.filter {
            it.enabled() == enabled
        }
    }

    suspend fun listAMT(): List<AMTDataDto> {
        val list = ArrayList<AMTDataDto>()
        amtPlugins.forEach {
            if (it.enabled())
                list.addAll(it.list())
        }
        return list
    }

    suspend fun getAMT(id: String) = listAMT().stream().filter {
        it.id == id
    }.findFirst()

    fun getSCMPlugins(enabled: Boolean) = run {
        scmPlugins.filter {
            it.enabled() == enabled
        }
    }

    fun getAMRPlugin(name: String) = amtPlugins.first { it.getName() == name }
}