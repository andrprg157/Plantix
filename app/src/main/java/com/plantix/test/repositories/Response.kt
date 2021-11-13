package com.plantix.test.repositories

sealed class Response<T>(val data :T? = null,val errormessage:String?=null)
{
    class Loading<T>:Response<T>()
    class Success<T>(data: T?=null):Response<T>(data=data)
    class Error<T>(errormessage: String?) :Response<T>(errormessage = errormessage)
}
