package com.dulfinne.taxi.rideservice.client.decoder

import com.dulfinne.taxi.rideservice.exception.ClientException
import com.dulfinne.taxi.rideservice.util.ExceptionKeys
import com.fasterxml.jackson.databind.ObjectMapper
import feign.Response
import feign.codec.ErrorDecoder
import java.lang.Exception

class ClientErrorDecoder : ErrorDecoder {

    private val objectMapper = ObjectMapper()

    override fun decode(methodKey: String?, response: Response?): Exception {
        val status = response?.status() ?: throw Exception()
        val body = response.body()?.asInputStream()?.reader()?.readText() ?: ExceptionKeys.UNKNOWN_ERROR
        val errorDetails = objectMapper.readValue(body, Map::class.java)
        val message = errorDetails["message"].toString()

        throw ClientException(status, message)
    }
}