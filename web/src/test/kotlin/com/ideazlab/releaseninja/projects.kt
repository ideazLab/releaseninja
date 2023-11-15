package com.ideazlab.releaseninja

import com.ideazlab.releaseninja.common.model.dto.ProjectDto
import com.ideazlab.releaseninja.common.service.ProjectService
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


@MicronautTest(environments = ["test"])
class Project{
    @Inject
    lateinit var projectService: ProjectService
    @Test
    fun testItWorks() {
        runBlocking {
            var test = ProjectDto(name = "test", description = "description")
            Assertions.assertNotNull(projectService.create(test))
        }
    }
}