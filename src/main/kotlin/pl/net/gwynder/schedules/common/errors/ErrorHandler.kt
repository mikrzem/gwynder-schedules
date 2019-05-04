package pl.net.gwynder.schedules.common.errors

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ErrorHandler {

    @ExceptionHandler(DataNotFound::class)
    fun dataNotFound(error: DataNotFound): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.message)
    }

    @ExceptionHandler(InvalidFormat::class)
    fun invalidFormat(error: InvalidFormat): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.message)
    }
}