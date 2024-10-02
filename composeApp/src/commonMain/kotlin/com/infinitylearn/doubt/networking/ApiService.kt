package com.infinitylearn.doubt.networking

class ApiService {

     fun getSubjectGradeMapping(): String{
         return getDoubtsBaseURL()+"meta/subjectsGradeMapping"
     }

    fun getAllDoubts() :String{
        return getDoubtsBaseURL()+"user/getDoubtsv2"
    }

    fun createDoubt() :String{
        return getDoubtsBaseURL()+"v2/createDoubt"
    }
}