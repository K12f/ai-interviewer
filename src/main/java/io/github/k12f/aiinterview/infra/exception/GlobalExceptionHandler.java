package io.github.k12f.aiinterview.infra.exception;

import io.github.k12f.aiinterview.infra.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Response<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var allErrors = e.getBindingResult().getAllErrors();
        var errorMsg = new StringBuilder();
        for (var error : allErrors) {
            errorMsg.append(error.getDefaultMessage()).append(" ");
        }
        return Response.failed(500, errorMsg.toString()).body();
    }


    @ExceptionHandler
    @SneakyThrows
    public Response<Map<String, String>> exceptionHandler(HttpServletRequest request, Exception e) {
        try {
            log.error(request.toString());
            log.error(e.getMessage());
            return Response.failed(500, "服务器异常，请联系管理员").body();
        } catch (Exception ee) {
            log.error(request.toString());
            log.error(ee.getMessage());
            return Response.failed(500, "服务器异常，请联系管理员").body();
        }
    }
}