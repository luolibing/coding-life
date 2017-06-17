//package cn.tim.restful.advice
//
//import org.apache.commons.logging.LogFactory
//import org.slf4j.Logger
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.ControllerAdvice
//import org.springframework.web.bind.annotation.ExceptionHandler
//import org.springframework.web.bind.annotation.ResponseBody
//
//import javax.servlet.http.HttpServletRequest
//
///**
// * Created by LuoLiBing on 16/3/16.
// */
//@ControllerAdvice(basePackages = {"me.ele.mars.webservice.web"})
//public class TestAdvice {
//    private static final Logger LOGGER = LogFactory.getLog(TestAdvice.class);
//
//
//    @ExceptionHandler(RuntimeException.class)
//    @ResponseBody
//    ResponseEntity handleControllerServiceException(HttpServletRequest request, Throwable th) {
//        RuntimeException service = (RuntimeException) th;
//        LOGGER.error("Controller ServiceException-Path:[{}]", request.getRequestURI());
//        LOGGER.error("Controller ServiceException - ", th);
//        return new ResponseEntity<>(service.getCode(), service.getMessage(), null);
//    }
//
//    @ExceptionHandler(SOAException.class)
//    @ResponseBody
//    ResponseEntity handleControllerSOAException(HttpServletRequest request, Throwable th) {
//        HttpStatus status = getStatus(request);
//        LOGGER.error("Controller SOAException-Path:[{}]", request.getRequestURI());
//        LOGGER.error("Controller SOAException - ", th);
//        return new ResponseEntity<>(Integer.valueOf(status.value()).toString(), "网络抖了一下,请稍后再试", null);
//    }
//
//    @ExceptionHandler(NoAvailableWorkerException.class)
//    @ResponseBody
//    ResponseEntity handleControllerNoAvailableWorkerException(HttpServletRequest request, Throwable th) {
//        HttpStatus status = getStatus(request);
//        LOGGER.error("Controller NoAvailableWorkerException-Path:[{}]", request.getRequestURI());
//        LOGGER.error("Controller NoAvailableWorkerException - ", th);
//        return new ResponseEntity<>(Integer.valueOf(status.value()).toString(), "当前请求过多,请求稍后再试", null);
//    }
//
//    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
//    @ResponseBody
//    ResponseEntity handleMethodArgumentNotValidException(HttpServletRequest request, Exception e) {
//
//        HttpStatus status = getStatus(request);
//        String message = "请求参数异常,请检查";
//        if (e instanceof MethodArgumentNotValidException) {
//            MethodArgumentNotValidException mexception = (MethodArgumentNotValidException) e;
//            message = mexception.getBindingResult().getFieldError().getDefaultMessage();
//        } else if (e instanceof BindException) {
//            BindException bexception = (BindException) e;
//            message = bexception.getBindingResult().getFieldError().getDefaultMessage();
//        }
//        LOGGER.error(" Controller Check Argument ErrorMsg:[{}] Path:[{}]", message, request.getRequestURI());
//        LOGGER.error("Controller Check Argument Error - ", e);
//        return new ResponseEntity<>(status.toString(), message, null);
//    }
//
//    @ExceptionHandler(value = {NoSuchRequestHandlingMethodException.class,
//        HttpRequestMethodNotSupportedException.class, HttpMediaTypeNotSupportedException.class,
//        HttpMediaTypeNotAcceptableException.class, MissingServletRequestParameterException.class,
//        ServletRequestBindingException.class, ConversionNotSupportedException.class, TypeMismatchException.class,
//        HttpMessageNotReadableException.class, HttpMessageNotWritableException.class,
//        MissingServletRequestPartException.class, NoHandlerFoundException.class})
//    @ResponseBody
//    ResponseEntity handleException(HttpServletRequest request, Throwable th) {
//        HttpStatus status = getStatus(request);
//        LOGGER.error("Controller Exception-Path:[{}]", request.getRequestURI());
//        LOGGER.error("Controller Exception  - ", th);
//        return new ResponseEntity<>(status.toString(), "请求参数异常,请检查", null);
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    ResponseEntity handleControllerException(HttpServletRequest request, Throwable th) {
//        HttpStatus status = getStatus(request);
//        LOGGER.error("Controller Path:[{}]", request.getRequestURI());
//        LOGGER.error("Controller Error - ", th);
//        return new ResponseEntity<>(status.toString(), "系统内部异常,请稍后再试", null);
//    }
//
//    private HttpStatus getStatus(HttpServletRequest request) {
//        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        if (statusCode == null) {
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return HttpStatus.valueOf(statusCode);
//    }