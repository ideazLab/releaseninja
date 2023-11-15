package com.ideazlab.releaseninja.common.utils

import com.ideazlab.releaseninja.common.exception.DataException
import com.ideazlab.releaseninja.common.exception.NotFoundException
import com.ideazlab.releaseninja.common.model.DataDto
import com.ideazlab.releaseninja.common.model.DataObj
import io.micronaut.data.repository.CrudRepository
import kotlinx.coroutines.runBlocking
import org.bson.types.ObjectId
import java.util.*

interface CoroutineCrudService<T : Any,E> {

    @Throws(Exception::class)
    suspend fun create(entity: T): T?
    @Throws(Exception::class)
    suspend fun read(key: String): Optional<T>{
        val id = MongoUtils.toObjectId(key)
        val repository = getRepository() as CrudRepository<DataObj, ObjectId>
        if(repository.existsById(id))
            return Optional.of(repository.findById(id).get().getDTO() as T)
        else
            throw NotFoundException("Not Found")
    }
    suspend fun update(entity: T){
        val vo = entity as DataDto
        if (entity.id!!.isBlank())
            throw Exception("Incomplete object")
        getRepository().findById(ObjectId(vo.id)).ifPresent {
            runBlocking {
                (it as DataObj).update(entity)
                getRepository().update(it)
            }
        }
    }

    suspend fun delete(entity: T) {
        testKey(entity as DataDto)
        try {
            getRepository().deleteById(ObjectId(entity.id))
        } catch (e: Exception) {
            throw DataException("Error deleting  ${entity.javaClass}")
        }
    }

    suspend fun existById(id: String) = getRepository().existsById(ObjectId(id))
    suspend fun list(): List<T> = getRepository().findAll().map { run { (it as DataObj).getDTO() as T } }
    suspend fun getRepository(): CrudRepository<E, ObjectId>

    @Throws(Exception::class)
    suspend fun testKey(entity: DataDto) {
        if (entity.id!!.isBlank() || !getRepository().existsById(ObjectId(entity.id)))
            throw Exception("Incomplete object")
    }
}