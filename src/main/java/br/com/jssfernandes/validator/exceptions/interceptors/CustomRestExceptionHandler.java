package br.com.jssfernandes.validator.exceptions.interceptors;

import br.com.jssfernandes.validator.presenters.ErrorPresenter;
import br.com.jssfernandes.validator.logger.Log;
import br.com.jssfernandes.validator.logger.LogRequest;
import br.com.jssfernandes.validator.logger.LogType;
import br.com.jssfernandes.validator.models.ErrorDefault;
import br.com.jssfernandes.validator.validators.exceptions.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
    private Log logger = new Log(CustomRestExceptionHandler.class, LogType.REQUEST);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErrorDefault> errorDefaults = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorDefaults.add(new ErrorDefault(ex.getBindingResult().getObjectName(), error.getField() + ": " + error.getDefaultMessage()));
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errorDefaults.add(new ErrorDefault(ex.getBindingResult().getObjectName(), error.getObjectName() + ": " + error.getDefaultMessage()));
        }
        ErrorPresenter presenter = new ErrorPresenter(errorDefaults);

        LogRequest log = new LogRequest(request, status);
        logger.error(log, ex);

        return handleExceptionInternal(ex, presenter, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ErrorPresenter> handleBusinessException(ValidationException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (ex.getValidator().getStatus() != null) {
            status = ex.getValidator().getStatus();
        }

        ErrorPresenter presenter = new ErrorPresenter();
        presenter.setStatus(String.valueOf(status.value()));
        if (ex.getValidator().hasErrors()) {
            presenter.setErrors(ex.getValidator().getErrors());
        } else {
            presenter.getErrors().add(new ErrorDefault("Validation", ex.getLocalizedMessage(), "002"));
        }

        LogRequest log = new LogRequest(request, status);
        logger.error(log, ex);

        return new ResponseEntity<ErrorPresenter>(presenter, new HttpHeaders(), status);
    }

//    @ExceptionHandler({FeignErrorException.class})
//    public ResponseEntity<ErrorPresenter> handleFeignErrorException(FeignErrorException ex, WebRequest request) {
//        LogRequest log = new LogRequest(request, ex.getStatus());
//        logger.error(log, ex);
//
//        ErrorPresenter presenter = ex.getErrorPresenter();
//        if (presenter == null) {
//            presenter = new ErrorPresenter();
//        }
//
//        presenter.setError(ex.getMessage());
//        presenter.setStatus(String.valueOf(ex.getStatus().value()));
//        presenter.setPath(ex.getUrl());
//
//        return new ResponseEntity<ErrorPresenter>(presenter, new HttpHeaders(), ex.getStatus());
//    }

}