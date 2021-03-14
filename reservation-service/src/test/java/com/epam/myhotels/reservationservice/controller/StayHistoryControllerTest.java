package com.epam.myhotels.reservationservice.controller;

import com.epam.myhotels.reservationservice.dto.StayHistoryDto;
import com.epam.myhotels.reservationservice.dto.mapper.StayHistoryDtoMapper;
import com.epam.myhotels.reservationservice.model.StayHistoryModel;
import com.epam.myhotels.reservationservice.service.StayHistoryService;
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

@WebMvcTest(StayHistoryController.class)
public class StayHistoryControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StayHistoryService stayHistoryService;
    @MockBean
    private StayHistoryDtoMapper stayHistoryDtoMapper;

    @Test
    public void success_createStayHistory() throws Exception {

        // stub

        // actual call

        // assertion

        // verification

        StayHistoryDto stayHistoryDto = easyRandom.nextObject(StayHistoryDto.class);
        StayHistoryModel stayHistoryModel = new StayHistoryModel();
        Mockito.when(stayHistoryService.save(any(StayHistoryModel.class))).thenReturn(stayHistoryModel);
        Mockito.when(stayHistoryDtoMapper.toDto(any(StayHistoryModel.class))).thenReturn(stayHistoryDto);
        Mockito.when(stayHistoryDtoMapper.toModel(any(StayHistoryDto.class))).thenReturn(stayHistoryModel);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/stayHistories")
                                                                      .content(asJsonString(stayHistoryDto))
                                                                      .contentType(MediaType.APPLICATION_JSON)
                                                                      .accept(MediaType.APPLICATION_JSON);
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated())
           .andExpect(MockMvcResultMatchers.header().exists("location"));
    }

    @Test
    public void bad_request_createStayHistory() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/stayHistories").content(asJsonString(new StayHistoryDto()))
                                          .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
           .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getStayHistoryByID() throws Exception {
        StayHistoryDto stayHistoryDto = easyRandom.nextObject(StayHistoryDto.class);
        stayHistoryDto.setId(1L);
        StayHistoryModel stayHistoryModel = easyRandom.nextObject(StayHistoryModel.class);

        Mockito.when(stayHistoryService.findById(any(Long.class))).thenReturn(stayHistoryModel);
        Mockito.when(stayHistoryDtoMapper.toDto(any(StayHistoryModel.class))).thenReturn(stayHistoryDto);
        Mockito.when(stayHistoryDtoMapper.toModel(any(StayHistoryDto.class))).thenReturn(stayHistoryModel);

        mvc.perform(MockMvcRequestBuilders.get("/stayHistories/{id}", 1L).accept(MediaType.APPLICATION_JSON))
           .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void getAll() throws Exception {
        List<StayHistoryDto> stayHistoryDtoList = easyRandom.objects(StayHistoryDto.class, 4)
                                                            .collect(Collectors.toList());

        List<StayHistoryModel> stayHistories = easyRandom.objects(StayHistoryModel.class, 2)
                                                         .collect(Collectors.toList());

        Mockito.when(stayHistoryService.findAll()).thenReturn(stayHistories);
        Mockito.when(stayHistoryDtoMapper.toDto(stayHistories)).thenReturn(stayHistoryDtoList);

        mvc.perform(MockMvcRequestBuilders.get("/stayHistories").accept(MediaType.APPLICATION_JSON))
           .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.stayHistories").exists())
           .andExpect(MockMvcResultMatchers.jsonPath("$.stayHistories[*].id").isNotEmpty());
    }

    @Test
    public void updateStayHistory() throws Exception {
        StayHistoryDto stayHistoryDto = easyRandom.nextObject(StayHistoryDto.class);
        stayHistoryDto.setId(1L);
        StayHistoryModel stayHistoryModel = easyRandom.nextObject(StayHistoryModel.class);

        Mockito.when(stayHistoryService.update(any(Long.class), any(StayHistoryModel.class)))
               .thenReturn(stayHistoryModel);
        Mockito.when(stayHistoryDtoMapper.toDto(any(StayHistoryModel.class))).thenReturn(stayHistoryDto);
        Mockito.when(stayHistoryDtoMapper.toModel(any(StayHistoryDto.class))).thenReturn(stayHistoryModel);

        MockHttpServletRequestBuilder updateRequest = MockMvcRequestBuilders.put("/stayHistories/{id}", 1)
                                                                            .content(asJsonString(stayHistoryDto))
                                                                            .contentType(MediaType.APPLICATION_JSON)
                                                                            .accept(MediaType.APPLICATION_JSON);
        mvc.perform(updateRequest).andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void deleteStayHistory() throws Exception {
        Mockito.when(stayHistoryService.delete(any(Long.class))).thenReturn(true);
        MockHttpServletRequestBuilder deleteRequest = MockMvcRequestBuilders.delete("/stayHistories/{id}", 1);
        mvc.perform(deleteRequest).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteHotel_NoContent() throws Exception {
        Mockito.when(stayHistoryService.delete(any(Long.class))).thenReturn(false);
        MockHttpServletRequestBuilder deleteRequest = MockMvcRequestBuilders.delete("/stayHistories/{id}", 1);
        mvc.perform(deleteRequest).andExpect(MockMvcResultMatchers.status().isNoContent());
    }


}