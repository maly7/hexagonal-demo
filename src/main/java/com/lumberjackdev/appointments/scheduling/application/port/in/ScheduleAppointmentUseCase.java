package com.lumberjackdev.appointments.scheduling.application.port.in;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public interface ScheduleAppointmentUseCase {

    boolean scheduleAppointment(ScheduleAppointmentCommand command);

    @Getter
    @Builder
    class ScheduleAppointmentCommand {
        private final LocalDateTime time;
        private final String requester;
    }
}
