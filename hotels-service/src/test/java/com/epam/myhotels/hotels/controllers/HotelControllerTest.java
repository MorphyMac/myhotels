package com.epam.myhotels.hotels.controllers;

import com.epam.myhotels.hotels.dto.HotelDto;
import com.epam.myhotels.hotels.dto.mapper.HotelDtoMapper;
import com.epam.myhotels.hotels.model.HotelModel;
import com.epam.myhotels.hotels.services.HotelService;
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

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(HotelController.class)
public class HotelControllerTest extends AbstractTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private HotelService hotelService;
    @MockBean
    private HotelDtoMapper hotelDtoMapper;

    @Test
    public void success_createHotel() throws Exception {
        HotelDto hotelDto = easyRandom.nextObject(HotelDto.class);
        HotelModel hotelModel = new HotelModel();

        Mockito.when(hotelService.save(any(HotelModel.class))).thenReturn(hotelModel);
        Mockito.when(hotelDtoMapper.toDto(any(HotelModel.class))).thenReturn(hotelDto);
        Mockito.when(hotelDtoMapper.toModel(any(HotelDto.class))).thenReturn(hotelModel);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/hotels").content(asJsonString(hotelDto))
                                                                      .contentType(MediaType.APPLICATION_JSON)
                                                                      .accept(MediaType.APPLICATION_JSON);
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated())
           .andExpect(MockMvcResultMatchers.header().exists("location"));
    }

    @Test
    public void bad_request_createHotel() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/hotels").content(asJsonString(null))
                                          .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
           .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getHotelByID() throws Exception {
        HotelModel hotelModel = new HotelModel();
        HotelDto hotelDto = easyRandom.nextObject(HotelDto.class);
        hotelDto.setId(1L);
        Mockito.when(hotelService.findById(any(Long.class))).thenReturn(hotelModel);
        Mockito.when(hotelDtoMapper.toDto(any(HotelModel.class))).thenReturn(hotelDto);
        Mockito.when(hotelDtoMapper.toModel(any(HotelDto.class))).thenReturn(hotelModel);

        mvc.perform(MockMvcRequestBuilders.get("/hotels/{id}", 1L).accept(MediaType.APPLICATION_JSON))
           .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void getAll() throws Exception {
        List<HotelDto> hotelDtoList = easyRandom.objects(HotelDto.class, 4).collect(Collectors.toList());
        List<HotelModel> hotels = easyRandom.objects(HotelModel.class, 2).collect(Collectors.toList());

        Mockito.when(hotelService.findAll()).thenReturn(hotels);
        Mockito.when(hotelDtoMapper.toDto(hotels)).thenReturn(hotelDtoList);

        mvc.perform(MockMvcRequestBuilders.get("/hotels").accept(MediaType.APPLICATION_JSON))
           .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.hotels").exists())
           .andExpect(MockMvcResultMatchers.jsonPath("$.hotels[*].id").isNotEmpty());
    }

    @Test
    public void updateHotel() throws Exception {
        HotelDto hotelDto = easyRandom.nextObject(HotelDto.class);
        hotelDto.setId(1L);
        hotelDto.setName("ITC Kohinoor");
        HotelModel hotelModel = easyRandom.nextObject(HotelModel.class);

        Mockito.when(hotelService.update(any(Long.class), any(HotelModel.class))).thenReturn(hotelModel);
        Mockito.when(hotelDtoMapper.toDto(any(HotelModel.class))).thenReturn(hotelDto);
        Mockito.when(hotelDtoMapper.toModel(any(HotelDto.class))).thenReturn(hotelModel);

        MockHttpServletRequestBuilder updateRequest = MockMvcRequestBuilders.put("/hotels/{id}", 1)
                                                                            .content(asJsonString(hotelDto))
                                                                            .contentType(MediaType.APPLICATION_JSON)
                                                                            .accept(MediaType.APPLICATION_JSON);
        mvc.perform(updateRequest).andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
           .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("ITC Kohinoor"));
    }

    @Test
    public void deleteHotel_OK() throws Exception {
        Mockito.when(hotelService.delete(any(Long.class))).thenReturn(true);
        MockHttpServletRequestBuilder deleteRequest = MockMvcRequestBuilders.delete("/hotels/{id}", 1);
        mvc.perform(deleteRequest).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteHotel_NoContent() throws Exception {
        Mockito.when(hotelService.delete(any(Long.class))).thenReturn(false);
        MockHttpServletRequestBuilder deleteRequest = MockMvcRequestBuilders.delete("/hotels/{id}", 1);
        mvc.perform(deleteRequest).andExpect(MockMvcResultMatchers.status().isNoContent());
    }


}