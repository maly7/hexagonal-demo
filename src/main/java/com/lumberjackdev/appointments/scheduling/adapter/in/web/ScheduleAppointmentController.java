package com.lumberjackdev.appointments.scheduling.adapter.in.web;

import com.lumberjackdev.appointments.scheduling.application.port.in.ScheduleAppointmentUseCase;
import com.lumberjackdev.appointments.scheduling.application.port.in.ScheduleAppointmentUseCase.ScheduleAppointmentCommand;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleAppointmentController {
    private final ScheduleAppointmentUseCase scheduleAppointmentUseCase;

    public ScheduleAppointmentController(ScheduleAppointmentUseCase scheduleAppointmentUseCase) {
        this.scheduleAppointmentUseCase = scheduleAppointmentUseCase;
    }

    @PostMapping("/appointments")
    @ResponseStatus(HttpStatus.CREATED)
    public void scheduleAppointment(@RequestBody ScheduleAppointmentRequest request) {
        var command = ScheduleAppointmentCommand.builder()
                .requester(request.getRequestedBy())
                .time(request.getRequestedTime())
                .build();
        scheduleAppointmentUseCase.scheduleAppointment(command);
    }
}
