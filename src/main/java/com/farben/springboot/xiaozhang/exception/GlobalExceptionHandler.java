package com.farben.springboot.xiaozhang.exception;

import com.farben.springboot.xiaozhang.common.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 处理参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException ex) {
//        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
//                .collect(Collectors.toMap(
//                        FieldError::getField,
//                        fieldError -> fieldError.getDefaultMessage() != null
//                                ? fieldError.getDefaultMessage()
//                                : "参数错误"
//                ));
        Map map = new HashMap();
        map.put("code",400);
        map.put("message", "参数校验失败");
        //map.put("errors", errors);
        map.put("errors", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);

    }

    // 处理自定义业务异常等

//    // 处理 MyBatis 数据不存在异常
//    @ExceptionHandler(DataNotFoundException.class)
//    public ResponseEntity<ApiResult<?>> handleDataNotFound(DataNotFoundException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(ApiResult.error(404, ex.getMessage()));
//    }

    // 处理数据库异常
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResult<?>> handleDataAccessException(DataAccessException ex) {
        log.error("数据库异常",ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResult.error(500, "数据库操作异常"));
    }

    // 处理 MyBatis 系统异常
    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<ApiResult<?>> handlePersistenceException(PersistenceException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResult.error(500, "持久化操作失败"));
    }
}
