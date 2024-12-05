package com.dulfinne.taxi.rideservice.client.decoder

import com.dulfinne.taxi.rideservice.exception.EntityNotFoundException
import com.dulfinne.taxi.rideservice.util.ExceptionKeys
import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.http.HttpStatus
import java.lang.Exception

class ClientErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String?, response: Response?): Exception {
        if (response!!.status() == HttpStatus.NOT_FOUND.value()) {
            return EntityNotFoundException(ExceptionKeys.PROFILE_NOT_FOUND)
        }
        return Exception()
    }
}