package com.epam.myhotels.reservationservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ReservationStatusController.class)
public class HealthStatusControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void status() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/reservations/status")).andDo(MockMvcResultHandlers.print())
           .andExpect(MockMvcResultMatchers.status().isOk());
    }
}