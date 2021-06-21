package com.lumberjackdev.appointments.scheduling.application.port.out;

import com.lumberjackdev.appointments.scheduling.domain.Appointment;

public interface CreateAppointmentPort {
    void createAppointment(Appointment appointment);
}
