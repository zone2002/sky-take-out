package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex 捕获的异常
     * @return 错误信息
     */
    @ExceptionHandler
    public Result<Void> exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }


    /**
     * 捕获Sql异常
     * @param ex 捕获的异常
     * @return 错误信息
     */
    @ExceptionHandler
    public Result<Void> exceptionHandler(SQLException ex){
        String message = ex.getMessage();

        //Duplicate entry 'zhangsan' for key 'employee.idx_username'
        //用户名重复
        if(message.contains("Duplicate entry")){
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        }


        return Result.error(MessageConstant.UNKNOWN_ERROR);

    }

}
