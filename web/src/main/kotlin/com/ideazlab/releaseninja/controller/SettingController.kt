package com.ideazlab.releaseninja.controller

import com.ideazlab.releaseninja.common.plugins.PluginManager
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.views.View


@Controller("/settings")
class SettingController(
    private val pluginManager: PluginManager
) {
    @Get(uris = ["", "/"])
    @View("settings")
    @Secured(*arrayOf("ADMIN"))
    fun index(): HttpResponse<Map<String, Any>> {
        val result = HashMap<String, Any>()


        return HttpResponse.ok(result)
    }
}