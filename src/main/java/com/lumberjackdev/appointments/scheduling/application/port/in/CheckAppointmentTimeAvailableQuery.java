package com.lumberjackdev.appointments.scheduling.application.port.in;

import java.time.LocalDateTime;

public interface CheckAppointmentTimeAvailableQuery {

    boolean isAppointmentAvailable(LocalDateTime appointmentTime);

}
