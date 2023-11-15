package com.ideazlab.releaseninja.common.utils

import com.ideazlab.releaseninja.common.api.response.BaseResponse
import io.micronaut.http.HttpResponse

interface BasedOperation<T> {
    suspend fun create(o: T): HttpResponse<out BaseResponse>
    suspend fun read(id: String): HttpResponse<BaseResponse>
    suspend fun list():HttpResponse<BaseResponse>
    suspend fun update(o: T):HttpResponse<out BaseResponse>
    suspend fun delete(id: String): HttpResponse<out BaseResponse>
}