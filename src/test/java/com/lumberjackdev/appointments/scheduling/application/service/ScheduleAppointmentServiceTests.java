package com.lumberjackdev.appointments.scheduling.application.service;

import com.github.javafaker.Faker;
import com.lumberjackdev.appointments.scheduling.application.port.in.ScheduleAppointmentUseCase.ScheduleAppointmentCommand;
import com.lumberjackdev.appointments.scheduling.application.port.in.CheckAppointmentTimeAvailableQuery;
import com.lumberjackdev.appointments.scheduling.application.port.out.CreateAppointmentPort;
import com.lumberjackdev.appointments.scheduling.domain.Appointment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ScheduleAppointmentServiceTests {
    static final Faker faker = Faker.instance();

    ScheduleAppointmentService subject;
    CreateAppointmentPort mockCreateAppointmentPort = mock(CreateAppointmentPort.class);
    CheckAppointmentTimeAvailableQuery mockCheckAppointmentTimeAvailableQuery = mock(CheckAppointmentTimeAvailableQuery.class);

    @BeforeEach
    void setup() {
        subject = new ScheduleAppointmentService(mockCreateAppointmentPort, mockCheckAppointmentTimeAvailableQuery);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(mockCreateAppointmentPort);
    }

    @Test
    void givenAppointmentTimeIsAvailable_thenScheduleAppointmentSucceeds() {
        var command = ScheduleAppointmentCommand
                .builder()
                .time(LocalDateTime.now())
                .requester(faker.internet().emailAddress())
                .build();
        when(mockCheckAppointmentTimeAvailableQuery.isAppointmentAvailable(command.getTime())).thenReturn(true);

        assertThat(subject.scheduleAppointment(command)).isTrue();
        verify(mockCreateAppointmentPort).createAppointment(new Appointment(command));
    }

    @Test
    void givenAppointmentTimeIsUnavailable_thenScheduleAppointmentFails() {
        var command = ScheduleAppointmentCommand
                .builder()
                .time(LocalDateTime.now())
                .requester(faker.internet().emailAddress())
                .build();
        when(mockCheckAppointmentTimeAvailableQuery.isAppointmentAvailable(command.getTime())).thenReturn(false);

        assertThrows(AppointmentUnavailableException.class, () -> {
           subject.scheduleAppointment(command);
        });
        verify(mockCreateAppointmentPort, never()).createAppointment(any());
    }
}