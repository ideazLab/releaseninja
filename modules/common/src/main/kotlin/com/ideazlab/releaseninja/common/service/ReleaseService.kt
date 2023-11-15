package com.ideazlab.releaseninja.common.service

import com.ideazlab.releaseninja.common.annotations.Service
import com.ideazlab.releaseninja.common.api.request.project.UpdateCurrentRelease
import com.ideazlab.releaseninja.common.api.response.BaseResponse
import com.ideazlab.releaseninja.common.model.domain.Release
import com.ideazlab.releaseninja.common.model.dto.ReleaseDto
import com.ideazlab.releaseninja.common.model.repo.ProjectRepository
import com.ideazlab.releaseninja.common.model.repo.ReleaseRepository
import com.ideazlab.releaseninja.common.utils.CoroutineCrudService
import org.bson.types.ObjectId

@Service
class ReleaseService(
    private val repository: ReleaseRepository,
    private val projectRepository: ProjectRepository
) : CoroutineCrudService<ReleaseDto, Release> {
    override suspend fun create(entity: ReleaseDto): ReleaseDto? {
        var release: ReleaseDto? = null
        entity.project?.let {
            it.id?.let {
                projectRepository.findById(ObjectId(it)).ifPresent {
                    release = repository.save(
                        Release(
                            version = entity.version, artifacts = entity.artifacts,
                            date = entity.date, project = it
                        )
                    ).getDTO()
                }
            }
        }
        if (release != null)
            return release
        throw Exception("Project not found error")
    }

    suspend fun updateCurrentRelease(update: UpdateCurrentRelease): BaseResponse {
        var response = BaseResponse(false, "Error updating current Release")
        projectRepository.findById(ObjectId(update.id)).also { projectOptional ->
            if (projectOptional.isPresent) {
                val project = projectOptional.get()
                repository.findById(ObjectId(update.currentRelease)).also {
                    if (it.isPresent) {
                        project.currentRelease = it.get()
                        projectRepository.update(project)
                        response.success = true
                        response.message = "Successfully updated current Release"
                    } else {
                        response.message = "Error finding release"
                    }
                }
            } else
                response.message = "Error finding Project"
        }
        return response
    }

    override suspend fun getRepository() = repository
}