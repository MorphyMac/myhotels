package com.epam.myhotels.reservationservice.controller;

import com.epam.myhotels.reservationservice.dto.ReservationDto;
import com.epam.myhotels.reservationservice.dto.mapper.ReservationDtoMapper;
import com.epam.myhotels.reservationservice.entity.ReservationMode;
import com.epam.myhotels.reservationservice.model.ReservationModel;
import com.epam.myhotels.reservationservice.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest extends AbstractTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservationService reservationService;
    @MockBean
    private ReservationDtoMapper reservationDtoMapper;

    @Test
    public void success_createReservation() throws Exception {
        ReservationDto reservationDto = easyRandom.nextObject(ReservationDto.class);
        ReservationModel reservationModel = new ReservationModel();

        Mockito.when(reservationService.save(any(ReservationModel.class))).thenReturn(reservationModel);
        Mockito.when(reservationDtoMapper.toDto(any(ReservationModel.class))).thenReturn(reservationDto);
        Mockito.when(reservationDtoMapper.toModel(any(ReservationDto.class))).thenReturn(reservationModel);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/reservations")
                                                                      .content(asJsonString(reservationDto))
                                                                      .contentType(MediaType.APPLICATION_JSON)
                                                                      .accept(MediaType.APPLICATION_JSON);
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated())
           .andExpect(MockMvcResultMatchers.header().exists("location"));
    }

    @Test
    public void bad_request_createReservation() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/reservations").content(asJsonString(new ReservationDto()))
                                          .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
           .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getReservationByID() throws Exception {
        ReservationModel reservationModel = new ReservationModel();
        ReservationDto reservationDto = easyRandom.nextObject(ReservationDto.class);
        reservationDto.setId(1L);
        Mockito.when(reservationService.findById(any(Long.class))).thenReturn(reservationModel);
        Mockito.when(reservationDtoMapper.toDto(any(ReservationModel.class))).thenReturn(reservationDto);
        Mockito.when(reservationDtoMapper.toModel(any(ReservationDto.class))).thenReturn(reservationModel);

        mvc.perform(MockMvcRequestBuilders.get("/reservations/{id}", 1L).accept(MediaType.APPLICATION_JSON))
           .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void updateReservation() throws Exception {
        ReservationDto reservationDto = easyRandom.nextObject(ReservationDto.class);
        reservationDto.setId(1L);
        reservationDto.setMode(ReservationMode.ONLINE);
        ReservationModel reservationModel = easyRandom.nextObject(ReservationModel.class);

        Mockito.when(reservationService.update(any(Long.class), any(ReservationModel.class)))
               .thenReturn(reservationModel);
        Mockito.when(reservationDtoMapper.toDto(any(ReservationModel.class))).thenReturn(reservationDto);
        Mockito.when(reservationDtoMapper.toModel(any(ReservationDto.class))).thenReturn(reservationModel);

        MockHttpServletRequestBuilder updateRequest = MockMvcRequestBuilders.put("/reservations/{id}", 1)
                                                                            .content(asJsonString(reservationDto))
                                                                            .contentType(MediaType.APPLICATION_JSON)
                                                                            .accept(MediaType.APPLICATION_JSON);
        mvc.perform(updateRequest).andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
           .andExpect(MockMvcResultMatchers.jsonPath("$.mode").value("ONLINE"));
    }
}