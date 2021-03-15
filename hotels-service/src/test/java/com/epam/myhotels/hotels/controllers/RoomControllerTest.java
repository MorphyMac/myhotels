package com.epam.myhotels.hotels.controllers;

import com.epam.myhotels.hotels.dto.RoomDto;
import com.epam.myhotels.hotels.dto.RoomTypeDto;
import com.epam.myhotels.hotels.dto.mapper.RoomDtoMapper;
import com.epam.myhotels.hotels.model.RoomModel;
import com.epam.myhotels.hotels.model.RoomTypeModel;
import com.epam.myhotels.hotels.services.RoomService;
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

@WebMvcTest(RoomController.class)
public class RoomControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RoomService roomService;
    @MockBean
    private RoomDtoMapper roomDtoMapper;

    @Test
    public void success_createRoom() throws Exception {

        // stub

        // actual call

        // assertion

        // verification

        RoomDto roomDto = easyRandom.nextObject(RoomDto.class);
        RoomTypeDto roomTypeDto = easyRandom.nextObject(RoomTypeDto.class);
        roomDto.setRoomType(roomTypeDto);

        RoomModel roomModel = new RoomModel();
        Mockito.when(roomService.save(any(RoomModel.class))).thenReturn(roomModel);
        Mockito.when(roomDtoMapper.toDto(any(RoomModel.class))).thenReturn(roomDto);
        Mockito.when(roomDtoMapper.toModel(any(RoomDto.class))).thenReturn(roomModel);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/rooms").content(asJsonString(roomDto))
                                                                      .contentType(MediaType.APPLICATION_JSON)
                                                                      .accept(MediaType.APPLICATION_JSON);
        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated())
           .andExpect(MockMvcResultMatchers.header().exists("location"));
    }

    @Test
    public void bad_request_createRoom() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/rooms").content(asJsonString(new RoomDto()))
                                          .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
           .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getRoomByID() throws Exception {
        RoomDto roomDto = easyRandom.nextObject(RoomDto.class);
        roomDto.setId(1L);
        roomDto.setRoomType(easyRandom.nextObject(RoomTypeDto.class));

        RoomModel roomModel = easyRandom.nextObject(RoomModel.class);
        roomModel.setRoomType(easyRandom.nextObject(RoomTypeModel.class));

        Mockito.when(roomService.findById(any(Long.class))).thenReturn(roomModel);
        Mockito.when(roomDtoMapper.toDto(any(RoomModel.class))).thenReturn(roomDto);
        Mockito.when(roomDtoMapper.toModel(any(RoomDto.class))).thenReturn(roomModel);

        mvc.perform(MockMvcRequestBuilders.get("/rooms/{id}", 1L).accept(MediaType.APPLICATION_JSON))
           .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void getAll() throws Exception {
        List<RoomDto> roomDtoList = easyRandom.objects(RoomDto.class, 4).parallel().map(roomDto -> {
            roomDto.setRoomType(easyRandom.nextObject(RoomTypeDto.class));
            return roomDto;
        }).collect(Collectors.toList());

        List<RoomModel> rooms = easyRandom.objects(RoomModel.class, 2).parallel().map(room -> {
            room.setRoomType(easyRandom.nextObject(RoomTypeModel.class));
            return room;
        }).collect(Collectors.toList());

        Mockito.when(roomService.findAll()).thenReturn(rooms);
        Mockito.when(roomDtoMapper.toDto(rooms)).thenReturn(roomDtoList);

        mvc.perform(MockMvcRequestBuilders.get("/rooms").accept(MediaType.APPLICATION_JSON))
           .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.rooms").exists())
           .andExpect(MockMvcResultMatchers.jsonPath("$.rooms[*].id").isNotEmpty());
    }

    @Test
    public void updateRoom() throws Exception {
        RoomDto roomDto = easyRandom.nextObject(RoomDto.class);
        roomDto.setId(1L);
        roomDto.setHotelId(100L);
        roomDto.setRoomNumber(100);
        roomDto.setRoomType(easyRandom.nextObject(RoomTypeDto.class));

        RoomModel roomModel = easyRandom.nextObject(RoomModel.class);
        roomModel.setRoomType(easyRandom.nextObject(RoomTypeModel.class));

        Mockito.when(roomService.update(any(Long.class), any(RoomModel.class))).thenReturn(roomModel);
        Mockito.when(roomDtoMapper.toDto(any(RoomModel.class))).thenReturn(roomDto);
        Mockito.when(roomDtoMapper.toModel(any(RoomDto.class))).thenReturn(roomModel);

        MockHttpServletRequestBuilder updateRequest = MockMvcRequestBuilders.put("/rooms/{id}", 1)
                                                                            .content(asJsonString(roomDto))
                                                                            .contentType(MediaType.APPLICATION_JSON)
                                                                            .accept(MediaType.APPLICATION_JSON);
        mvc.perform(updateRequest).andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.hotelId").value(100))
           .andExpect(MockMvcResultMatchers.jsonPath("$.roomNumber").value(100));
    }

    @Test
    public void deleteRoom() throws Exception {
        Mockito.when(roomService.delete(any(Long.class))).thenReturn(true);
        MockHttpServletRequestBuilder deleteRequest = MockMvcRequestBuilders.delete("/rooms/{id}", 1);
        mvc.perform(deleteRequest).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteHotel_NoContent() throws Exception {
        Mockito.when(roomService.delete(any(Long.class))).thenReturn(false);
        MockHttpServletRequestBuilder deleteRequest = MockMvcRequestBuilders.delete("/rooms/{id}", 1);
        mvc.perform(deleteRequest).andExpect(MockMvcResultMatchers.status().isNoContent());
    }


}