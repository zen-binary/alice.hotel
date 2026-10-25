package com.alice.hotel.api.config.security.handler;

import com.alice.hotel.api.common.ApiResponse;
import com.alice.hotel.api.common.ApiStatus;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler (CustomException.class)
    public ApiResponse<Object> handleCustomException(CustomException ex) {
        ApiStatus apiStatus = ex.getApiStatus();
        logger.error("CustomException: ", ex);
        return new ApiResponse<>(apiStatus, null);
    }

    @ExceptionHandler (Exception.class)
    public ApiResponse<Object> handleAllExceptions(Exception ex) {
        ApiStatus apiStatus = ApiStatus.INTERNAL_SERVER_ERROR;
        // Log the exception if needed
        logger.error("INTERNAL_SERVER_ERROR: ", ex);
        return new ApiResponse<>(apiStatus, null);
    }

    @ExceptionHandler (EntityNotFoundException.class)
    public ApiResponse<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        ApiStatus apiStatus = ApiStatus.BAD_REQUEST_VALID;
        logger.error("Error: ", ex);
        return new ApiResponse<>(apiStatus, null);
    }

    @ExceptionHandler (DataIntegrityViolationException.class)
    public ApiResponse<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ApiStatus apiStatus = ApiStatus.BAD_REQUEST_VALID;
        logger.error("Error: ", ex);
        return new ApiResponse<>(apiStatus, null);
    }

    @ExceptionHandler (ConstraintViolationException.class)
    public ApiResponse<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        ApiStatus apiStatus = ApiStatus.BAD_REQUEST_VALID;
        logger.error("Error: ", ex);
        return new ApiResponse<>(apiStatus, null);
    }

    @ExceptionHandler (OptimisticLockException.class)
    public ApiResponse<Object> handleOptimisticLockException(OptimisticLockException ex) {
        ApiStatus apiStatus = ApiStatus.BAD_REQUEST_VALID;
        logger.error("Error: ", ex);
        return new ApiResponse<>(apiStatus, null);
    }

    @ExceptionHandler (TransactionSystemException.class)
    public ApiResponse<Object> handleTransactionSystemException(TransactionSystemException ex) {
        ApiStatus apiStatus = ApiStatus.BAD_REQUEST_VALID;
        logger.error("Error: ", ex);
        return new ApiResponse<>(apiStatus, null);
    }

}
