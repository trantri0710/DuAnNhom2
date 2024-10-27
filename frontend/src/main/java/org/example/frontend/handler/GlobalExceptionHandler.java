package org.example.frontend.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) // Xử lý tất cả các loại Exception (ngoại lệ chung)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Đặt trạng thái HTTP trả về là 500 (Internal Server Error)
    public String handleException(Exception ex) {
        return "error"; // Trả về tên trang hiển thị lỗi 500
    }

    @ExceptionHandler(NoResourceFoundException.class)  // Xử lý ngoại lệ NoResourceFoundException (lỗi không tìm thấy tài nguyên, lỗi 404)
    @ResponseStatus(HttpStatus.NOT_FOUND) // Đặt trạng thái HTTP trả về là 404 (Not Found)
    public String handleNoHandlerFoundException(Exception ex) {
        return "error"; // Trả về tên trang hiển thị lỗi 404
    }
}
