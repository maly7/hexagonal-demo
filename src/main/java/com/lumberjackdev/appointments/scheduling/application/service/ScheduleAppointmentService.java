package com.lumberjackdev.appointments.scheduling.application.service;

import com.lumberjackdev.appointments.scheduling.application.port.in.ScheduleAppointmentUseCase;
import com.lumberjackdev.appointments.scheduling.application.port.in.CheckAppointmentTimeAvailableQuery;
import com.lumberjackdev.appointments.scheduling.application.port.out.CreateAppointmentPort;
import com.lumberjackdev.appointments.scheduling.domain.Appointment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ScheduleAppointmentService implements ScheduleAppointmentUseCase {
    private final CreateAppointmentPort createAppointmentPort;
    private final CheckAppointmentTimeAvailableQuery checkAppointmentTimeAvailableQuery;

    public ScheduleAppointmentService(CreateAppointmentPort createAppointmentPort,
                                      CheckAppointmentTimeAvailableQuery checkAppointmentTimeAvailableQuery) {
        this.createAppointmentPort = createAppointmentPort;
        this.checkAppointmentTimeAvailableQuery = checkAppointmentTimeAvailableQuery;
    }

    @Override
    public boolean scheduleAppointment(ScheduleAppointmentCommand command) {
        if (!checkAppointmentTimeAvailableQuery.isAppointmentAvailable(command.getTime())) {
            throw new AppointmentUnavailableException(command.getRequester(), command.getTime());
        }

        createAppointmentPort.createAppointment(new Appointment(command));
        return true;
    }
}
