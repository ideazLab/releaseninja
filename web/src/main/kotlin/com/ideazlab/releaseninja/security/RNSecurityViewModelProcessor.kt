package com.ideazlab.releaseninja.security

import com.ideazlab.releaseninja.common.plugins.PluginManager
import com.ideazlab.releaseninja.common.service.ProjectService
import com.ideazlab.releaseninja.common.utils.ArtifactType
import com.ideazlab.releaseninja.common.utils.ArtifactVisibility
import com.ideazlab.releaseninja.common.utils.Status
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.core.util.StringUtils
import io.micronaut.http.HttpRequest
import io.micronaut.security.filters.SecurityFilter
import io.micronaut.security.utils.SecurityService
import io.micronaut.views.ModelAndView
import io.micronaut.views.model.ViewModelProcessor
import io.micronaut.views.model.security.SecurityViewModelProcessor
import io.micronaut.views.model.security.SecurityViewModelProcessorConfiguration
import io.micronaut.views.model.security.SecurityViewModelProcessorConfigurationProperties
import jakarta.inject.Singleton
import kotlinx.coroutines.runBlocking

@Singleton
@Requires(property = SecurityViewModelProcessorConfigurationProperties.PREFIX + ".enabled", notEquals = StringUtils.FALSE)
@Requires(beans = [SecurityFilter::class, SecurityService::class, SecurityViewModelProcessorConfiguration::class])
@Replaces(SecurityViewModelProcessor::class)
class RNSecurityViewModelProcessor(
    private val securityViewModelProcessorConfiguration: SecurityViewModelProcessorConfiguration,
    private val securityService: SecurityService,
    private val pluginManager: PluginManager,
    private val projectService: ProjectService,
) : ViewModelProcessor<MutableMap<String, Any>>{
    override fun process(request: HttpRequest<*>, modelAndView: ModelAndView<MutableMap<String, Any>>) {
        val authentication = securityService.authentication
        if (authentication.isPresent) {
            val securityModel: MutableMap<String, Any> = HashMap()
            securityModel[securityViewModelProcessorConfiguration.principalNameKey] = authentication.get().name
            securityModel[securityViewModelProcessorConfiguration.attributesKey] = authentication.get().attributes
            val viewModel = modelAndView.model.orElseGet {
                run{
                    val newModel = java.util.HashMap<String, Any>(1)
                    modelAndView.setModel(newModel)
                     newModel
                }
            }
            try {
                viewModel.putIfAbsent(securityViewModelProcessorConfiguration.securityKey, securityModel)
                viewModel["amtplugins"] = pluginManager.getAMTPlugins(true)
                viewModel["artifactTypes"] = ArtifactType.values()
                viewModel["artifactVisibilities"] = ArtifactVisibility.values()
                viewModel["statuses"] = Status.values()
                runBlocking {
                    viewModel["amts"] = pluginManager.listAMT()
                    viewModel["projects"] = projectService.list()

                }
            } catch (ex: UnsupportedOperationException) {
                val modifiableModel = HashMap(viewModel)
                modifiableModel.putIfAbsent(securityViewModelProcessorConfiguration.securityKey, securityModel)
                modelAndView.setModel(modifiableModel)
            }
        }
    }
}