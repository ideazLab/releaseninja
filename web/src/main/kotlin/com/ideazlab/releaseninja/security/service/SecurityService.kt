package com.ideazlab.releaseninja.security.service

import com.ideazlab.releaseninja.common.exception.AuthenticationException
import com.ideazlab.releaseninja.common.model.dto.SecurityDto
import com.ideazlab.releaseninja.common.utils.BCryptPasswordEncoderService
import com.ideazlab.releaseninja.common.utils.CoroutineCrudService
import com.ideazlab.releaseninja.security.domain.Security
import com.ideazlab.releaseninja.security.repo.SecurityRepository
import io.micronaut.security.authentication.AuthenticationFailureReason
import jakarta.inject.Singleton
import org.bson.types.ObjectId
import java.util.*

@Singleton
class SecurityService(
    private val repository: SecurityRepository,
    private val passwordEncoderService: BCryptPasswordEncoderService
) : CoroutineCrudService<SecurityDto, Security> {
    suspend fun existByUsername(username:String) = repository.existsByUsername(username = username)
    suspend fun findByUsername(username: String) = repository.findByUsername(username)
    @Throws(Exception::class)
    override suspend fun create(entity: SecurityDto): SecurityDto {
        if(repository.existsByUsername(entity.username))
            throw Exception("UserEntity ${entity.username} already exists")
        val userEntity = Security (username = entity.username, password = passwordEncoderService.encode(entity.password))
        userEntity.update(entity)
        return repository.save(userEntity).getDTO()
    }
    suspend fun  updatePassword(entity: SecurityDto){
        if(entity.username.isBlank() || !repository.existsByUsername(entity.username))
            throw Exception("Incomplete object")
        repository.findByUsername(entity.username).ifPresent {
            run {
                it.update(entity)
                it.password = passwordEncoderService.encode(entity.password)
                repository.update(it)
            }
        }
    }
    @Throws(AuthenticationException::class)
    suspend fun isValidLogin(username: String,password:String):Boolean{
        if(!existByUsername(username))
            throw AuthenticationException(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH)
        val found = repository.findByUsername(username).get()
        if(!passwordEncoderService.matches(password,found.password))
            throw AuthenticationException(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH)
        return true
    }
    override suspend fun getRepository() = repository
}