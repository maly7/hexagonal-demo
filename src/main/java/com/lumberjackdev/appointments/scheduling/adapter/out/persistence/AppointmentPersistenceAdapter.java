package com.lumberjackdev.appointments.scheduling.adapter.out.persistence;

import com.lumberjackdev.appointments.scheduling.application.port.in.CheckAppointmentTimeAvailableQuery;
import com.lumberjackdev.appointments.scheduling.application.port.out.CreateAppointmentPort;
import com.lumberjackdev.appointments.scheduling.domain.Appointment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AppointmentPersistenceAdapter implements CheckAppointmentTimeAvailableQuery, CreateAppointmentPort {
    private final AppointmentRepository appointmentRepository;

    public AppointmentPersistenceAdapter(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public boolean isAppointmentAvailable(LocalDateTime appointmentTime) {
        return !appointmentRepository.existsByAppointmentTime(appointmentTime);
    }

    @Override
    public void createAppointment(Appointment appointment) {
        var appointmentEntity = AppointmentEntity.builder()
                .appointmentTime(appointment.getTime())
                .requesterEmail(appointment.getRequester())
                .build();
        appointmentRepository.save(appointmentEntity);
    }
}
