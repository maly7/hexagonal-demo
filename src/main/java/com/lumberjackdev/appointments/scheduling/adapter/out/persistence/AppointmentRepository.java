package com.lumberjackdev.appointments.scheduling.adapter.out.persistence;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface AppointmentRepository extends CrudRepository<AppointmentEntity, String> {
    boolean existsByAppointmentTime(LocalDateTime appointmentTime);
}
