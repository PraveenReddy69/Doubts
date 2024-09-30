package com.infinitylearn.doubt.networking

class ApiService {

     fun getSubjectGradeMapping(): String{
         return getDoubtsBaseURL()+"meta/subjectsGradeMapping"
     }

    fun getAllDoubts() :String{
        return getDoubtsBaseURL()+"user/getDoubtsv2"
    }
}