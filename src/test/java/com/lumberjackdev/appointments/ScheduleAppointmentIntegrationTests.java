package com.lumberjackdev.appointments;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.lumberjackdev.appointments.scheduling.adapter.in.web.ScheduleAppointmentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DirtiesContext
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = IntegrationTestInitializer.class)
public class ScheduleAppointmentIntegrationTests {
    static final Faker faker = Faker.instance();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void scheduleAppointmentSucceeds() throws Exception {
        var requestedAppointment = ScheduleAppointmentRequest.builder()
                .requestedBy(faker.internet().emailAddress())
                .requestedTime(LocalDateTime.now().plusDays(20))
                .build();

        mockMvc.perform(
                post("/appointments")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestedAppointment)))
                .andExpect(status().isCreated());
    }

    @Test
    void scheduleAppointmentFails() throws Exception {
        var requestedAppointment = ScheduleAppointmentRequest.builder()
                .requestedBy(faker.internet().emailAddress())
                .requestedTime(LocalDateTime.now().plusDays(15))
                .build();

        var failedRequestAppointment = ScheduleAppointmentRequest.builder()
                .requestedBy(faker.internet().emailAddress())
                .requestedTime(requestedAppointment.getRequestedTime())
                .build();

        mockMvc.perform(
                post("/appointments")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestedAppointment)))
                .andExpect(status().isCreated());

        mockMvc.perform(
                post("/appointments")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(failedRequestAppointment)))
                .andExpect(status().is4xxClientError());
    }
}
