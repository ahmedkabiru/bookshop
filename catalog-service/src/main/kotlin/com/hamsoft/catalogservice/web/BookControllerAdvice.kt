package com.hamsoft.catalogservice.web

import com.hamsoft.catalogservice.domain.BookAlreadyExistException
import com.hamsoft.catalogservice.domain.BookNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class BookControllerAdvice {

    @ExceptionHandler(BookNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun bookNotFoundHandler(ex: BookNotFoundException): String? {
        return  ex.message
    }

    @ExceptionHandler(BookAlreadyExistException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun bookAlreadyExistHandler(ex: BookAlreadyExistException): String? {
        return ex.message
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): MutableMap<String, String?> {
        val errors = mutableMapOf<String, String?>()
        ex.bindingResult.allErrors.forEach {
            error -> run {
                val fieldName = (error as FieldError).field
                val errorMessage = error.getDefaultMessage()
                errors[fieldName] = errorMessage
            }
        }
        return errors;
    }
}