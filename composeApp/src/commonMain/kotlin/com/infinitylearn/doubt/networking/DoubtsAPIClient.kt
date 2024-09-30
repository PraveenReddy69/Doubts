package com.infinitylearn.doubt.networking

import com.infinitylearn.doubt.model.GetDoubtsRequest
import com.infinitylearn.doubt.model.MyQuestionResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get

import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText

import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import util.NetworkError
import util.Result

class DoubtsAPIClient(
    private val httpClient: HttpClient
) {

    suspend fun getSubjectsMapping(grade:Int, tenantID:Int): Result<List<SubjectGradeMappingItem>, NetworkError> {
        val response = try {
            httpClient.get(urlString = ApiService().getSubjectGradeMapping()) {
                parameter("gradeId", grade)
                parameter("tenantId", tenantID)
            }
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }

        return when (response.status.value) {
            in 200..299 -> {
                val data = response.body<List<SubjectGradeMappingItem>>()
                Result.Success(data)
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }


    suspend fun getAllDoubts(getDoubtsRequest: GetDoubtsRequest): Result<MyQuestionResponse, NetworkError> {
        val response = try {
            httpClient.post(urlString = ApiService().getAllDoubts()) {
                setBody(getDoubtsRequest)

            }
        } catch (e: UnresolvedAddressException) {
            println("------------AA-------------${e.message}")
            return Result.Error(NetworkError.NO_INTERNET)

        } catch (e: SerializationException) {
            println("-------------------------${e.message}")
            return Result.Error(NetworkError.SERIALIZATION)

        }catch (e:SocketTimeoutException){
            println("-------------------------${e.message}")
            return Result.Error(NetworkError.REQUEST_TIMEOUT)
        }catch (e:Exception){
            println("-------------------------${e.message}")
            return Result.Error(NetworkError.UNAUTHORIZED)
        }


        return when (response.status.value) {
            in 200..299 -> {
                val data = response.body<MyQuestionResponse>()
                Result.Success(data)
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> {
                // Log detailed information about the response in case of unknown errors
                val responseBody = response.bodyAsText()
                println("Unknown error - Status: ${response.status.value}, Response: $responseBody")
                Result.Error(NetworkError.UNKNOWN)
            }
        }
    }

}