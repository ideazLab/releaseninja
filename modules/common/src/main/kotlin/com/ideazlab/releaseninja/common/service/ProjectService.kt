package com.ideazlab.releaseninja.common.service

import com.ideazlab.releaseninja.common.annotations.Service
import com.ideazlab.releaseninja.common.api.request.project.UpdateProjectGeneralInfo
import com.ideazlab.releaseninja.common.api.response.BaseResponse
import com.ideazlab.releaseninja.common.model.domain.Project
import com.ideazlab.releaseninja.common.model.dto.ProjectDto
import com.ideazlab.releaseninja.common.model.repo.ProjectRepository
import com.ideazlab.releaseninja.common.utils.CoroutineCrudService
import org.bson.types.ObjectId

@Service
class ProjectService(
    private val repository: ProjectRepository
): CoroutineCrudService<ProjectDto, Project> {
    override suspend fun create(entity: ProjectDto): ProjectDto {
        if (repository.existsByName(entity.name))
            throw Exception("Project ${entity.name} already exists")
        val project = Project(name = entity.name, description = entity.description)
        project.update(entity)
        return repository.save(project).getDTO()
    }

    fun update(update: UpdateProjectGeneralInfo): BaseResponse {
        if (repository.existsById(ObjectId(update.id))) {
            val project = repository.findById(ObjectId(update.id)).get()
            project.name = update.name
            project.description = update.description
            project.status = update.status
            update.amt?.let {
                if (it.isNotBlank())
                    project.amt = ObjectId(it)
                else
                    project.amt = null
            }
            repository.update(project)
            return BaseResponse(true, "Successfully Updated project ${update.name}")
        } else {
            return BaseResponse(false, "Project ${update.name} not found")
        }
    }

    fun existByName(name: String) = repository.existsByName(name)
    override suspend fun getRepository() = repository
}