package com.ideazlab.releaseninja.common.utils

import com.ideazlab.releaseninja.common.exception.DataException
import org.bson.types.ObjectId

object MongoUtils {

    fun toObjectId(id:String):ObjectId{
        try {
            return ObjectId(id)
        }catch (e:Exception){
            throw DataException(message = "Invalid data request (index invalid)")
        }
    }
}