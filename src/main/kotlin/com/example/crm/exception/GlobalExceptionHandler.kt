package com.example.crm.exception

import io.jsonwebtoken.ExpiredJwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [NoSuchElementException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNoSuchElementException(e: NoSuchElementException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [IllegalArgumentException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity("Bad Request: ${e.message}", HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [RuntimeException::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleRuntimeException(e: RuntimeException): ResponseEntity<String> {
        return ResponseEntity("Internal Server Error: ${e.message}", HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(value = [ExpiredJwtException::class])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleTokenExtractionException(e: ExpiredJwtException): ResponseEntity<String> {
        return ResponseEntity("Authentication attempt failed: ${e.message}", HttpStatus.UNAUTHORIZED)
    }
    @ExceptionHandler(value = [BadCredentialsException::class])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleAccessDeniedException(e: BadCredentialsException): ResponseEntity<String> {
        return ResponseEntity("Access is denied: ${e.message}", HttpStatus.UNAUTHORIZED)
    }
    @ExceptionHandler(value = [NullPointerException::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleNullPointerException(e: NullPointerException): ResponseEntity<String> {
        return ResponseEntity("Null Pointer Exception: ${e.message}", HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<String> {
        return ResponseEntity("Bad Request: ${e.message}", HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<String> {
        return ResponseEntity("Bad Request: ${e.bindingResult.fieldError?.defaultMessage}", HttpStatus.BAD_REQUEST)
    }
}
