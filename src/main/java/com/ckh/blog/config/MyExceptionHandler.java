package com.ckh.blog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

//ControllerAdvice:类注解,作用于 整个Spring工程,定义了一个全局的异常处理器
@ControllerAdvice
public class MyExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //ExceptionHandler:方法注解, 作用于 Controller级别，为一个Controller定义一个异常处理器.
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Request URL:{},Exception:{}", request.getRequestURI(), e.getMessage());
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) !=null) {
            throw e;
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURI());
        mv.addObject("exception", e);
        mv.setViewName("error/error");
        return mv;
    }
}
