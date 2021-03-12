package com.epam.myhotels.hotels.service;

import com.epam.myhotels.hotels.entity.Room;
import com.epam.myhotels.hotels.exception.RoomNotFoundException;
import com.epam.myhotels.hotels.model.RoomModel;
import com.epam.myhotels.hotels.model.mapper.RoomModelMapper;
import com.epam.myhotels.hotels.repository.RoomRepository;
import com.epam.myhotels.hotels.services.RoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;
    @Mock
    private RoomModelMapper roomModelMapper;

    private RoomService roomService;

    @BeforeEach
    public void setUp() {
        this.roomService = new RoomService();
        this.roomService.setRoomRepository(roomRepository);
        this.roomService.setRoomModelMapper(roomModelMapper);
    }


    @Test
    public void save() {
        RoomModel roomModel = mock(RoomModel.class);
        Mockito.when(roomModel.getId()).thenReturn(1L);
        Mockito.when(roomModel.getHotelId()).thenReturn(1L);
        Mockito.when(roomModel.getRoomNumber()).thenReturn(1);

        Room roomEntity = mock(Room.class);

        Mockito.when(roomModelMapper.toEntity(ArgumentMatchers.any(RoomModel.class))).thenReturn(roomEntity);
        Mockito.when(roomModelMapper.toModel(ArgumentMatchers.any(Room.class))).thenReturn(roomModel);
        Mockito.when(roomRepository.save(ArgumentMatchers.any(Room.class))).thenReturn(roomEntity);

        RoomModel savedRoom = roomService.save(new RoomModel());

        Assertions.assertEquals(1L, savedRoom.getId());
        Assertions.assertEquals(1L, savedRoom.getHotelId());
        Assertions.assertEquals(1, savedRoom.getRoomNumber());
    }

    @Test
    public void findById() {
        RoomModel roomModel = mock(RoomModel.class);
        Mockito.when(roomModel.getId()).thenReturn(1L);
        Mockito.when(roomModel.getHotelId()).thenReturn(1L);
        Mockito.when(roomModel.getRoomNumber()).thenReturn(1);

        Room roomEntity = mock(Room.class);

        Mockito.when(roomRepository.findById(ArgumentMatchers.any(Long.class)))
               .thenReturn(Optional.ofNullable(roomEntity));
        Mockito.when(roomModelMapper.toModel(ArgumentMatchers.any(Room.class))).thenReturn(roomModel);

        RoomModel savedRoom = roomService.findById(1L);

        Assertions.assertEquals(1L, savedRoom.getId());
        Assertions.assertEquals(1L, savedRoom.getHotelId());
        Assertions.assertEquals(1, savedRoom.getRoomNumber());
    }

    @Test
    public void findById_Not_Found() {
        Mockito.when(roomRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(RoomNotFoundException.class, () -> roomService.findById(1L), "Room not found");
    }

    @Test
    public void update_Not_Found() {
        Mockito.when(roomRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(RoomNotFoundException.class, () -> roomService
                .update(1L, mock(RoomModel.class)), "Room not found");
    }

    @Test
    public void update() {
        RoomModel roomModel = mock(RoomModel.class);
        Mockito.when(roomModel.getId()).thenReturn(100L);
        Mockito.when(roomModel.getHotelId()).thenReturn(1L);
        Mockito.when(roomModel.getRoomNumber()).thenReturn(1);

        Room roomEntity = mock(Room.class);

        Mockito.when(roomRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(roomEntity));
        Mockito.when(roomModelMapper.toEntity(ArgumentMatchers.any(RoomModel.class))).thenReturn(roomEntity);
        Mockito.when(roomRepository.save(ArgumentMatchers.any(Room.class))).thenReturn(roomEntity);
        Mockito.when(roomModelMapper.toModel(ArgumentMatchers.any(Room.class))).thenReturn(roomModel);

        RoomModel update = roomService.update(100L, new RoomModel());

        Assertions.assertEquals(100L, update.getId());
        Assertions.assertEquals(1L, update.getHotelId());
        Assertions.assertEquals(1, update.getRoomNumber());

        verify(roomRepository).findById(any(Long.class));
        verify(roomRepository).save(any(Room.class));
        verify(roomModelMapper).toEntity(any(RoomModel.class));
        verify(roomModelMapper).toModel(any(Room.class));
    }

    @Test
    public void delete() {
        Room roomEntity = mock(Room.class);

        Mockito.when(roomRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(roomEntity));

        boolean delete = roomService.delete(1L);

        Assertions.assertTrue(delete);

        verify(roomRepository).findById(any(Long.class));
        verify(roomRepository).delete(any(Room.class));
    }

    @Test
    public void delete_No_Content() {
        Mockito.when(roomRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

        boolean delete = roomService.delete(1L);

        Assertions.assertFalse(delete);

        verify(roomRepository).findById(any(Long.class));
        verify(roomRepository, times(0)).delete(any(Room.class));
    }

    @Test
    public void findAll() {
        List<Room> entities = new ArrayList<>();
        entities.add(mock(Room.class));
        List<Room> entitiesSpy = spy(entities);
        Mockito.when(roomRepository.findAll()).thenReturn(entitiesSpy);

        List<RoomModel> models = new ArrayList<>();
        RoomModel roomModel = mock(RoomModel.class);
        models.add(roomModel);
        List<RoomModel> modelsSpy = spy(models);
        Mockito.when(roomModelMapper.toModels(entitiesSpy)).thenReturn(modelsSpy);

        List<RoomModel> all = roomService.findAll();

        Assertions.assertNotNull(all);

        verify(roomRepository).findAll();
    }

    @Test
    public void findAll_empty() {
        List<Room> entities = new ArrayList<>();
        Mockito.when(roomRepository.findAll()).thenReturn(entities);

        List<RoomModel> all = roomService.findAll();

        Assertions.assertNotNull(all);
        Assertions.assertTrue(all.isEmpty());

        verify(roomRepository).findAll();
    }

}