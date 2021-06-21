package com.lumberjackdev.appointments;

import com.lumberjackdev.appointments.scheduling.application.service.AppointmentUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingAdvice {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AppointmentUnavailableException.class)
    void handleConflict() {
        
    }
}
