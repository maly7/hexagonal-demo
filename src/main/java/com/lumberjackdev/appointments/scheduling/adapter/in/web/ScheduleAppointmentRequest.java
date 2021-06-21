package com.lumberjackdev.appointments.scheduling.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleAppointmentRequest {
    private String requestedBy;
    private LocalDateTime requestedTime;
}
