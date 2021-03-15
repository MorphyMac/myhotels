package com.epam.myhotels.hotels.service;

import com.epam.myhotels.hotels.entity.Hotel;
import com.epam.myhotels.hotels.exception.HotelNotFoundException;
import com.epam.myhotels.hotels.model.HotelModel;
import com.epam.myhotels.hotels.model.mapper.HotelModelMapper;
import com.epam.myhotels.hotels.repository.HotelRepository;
import com.epam.myhotels.hotels.services.HotelService;
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
public class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private HotelModelMapper hotelModelMapper;

    private HotelService hotelService;

    @BeforeEach
    public void setUp() {
        this.hotelService = new HotelService();
        this.hotelService.setHotelRepository(hotelRepository);
        this.hotelService.setHotelModelMapper(hotelModelMapper);
    }

    @Test
    public void save() {
        Hotel hotelEntity = mock(Hotel.class);
        HotelModel hotelModel = mock(HotelModel.class);
        Mockito.when(hotelModel.getId()).thenReturn(1L);
        Mockito.when(hotelModelMapper.toEntity(ArgumentMatchers.any(HotelModel.class))).thenReturn(hotelEntity);
        Mockito.when(hotelModelMapper.toModel(ArgumentMatchers.any(Hotel.class))).thenReturn(hotelModel);
        Mockito.when(hotelRepository.save(ArgumentMatchers.any(Hotel.class))).thenReturn(hotelEntity);

        HotelModel savedHotel = hotelService.save(hotelModel);

        Assertions.assertEquals(1L, savedHotel.getId());

        verify(hotelModelMapper).toEntity(hotelModel);
        verify(hotelModelMapper).toModel(hotelEntity);
        verify(hotelRepository).save(hotelEntity);
    }

    @Test
    public void findById() {
        HotelModel hotelModel = mock(HotelModel.class);
        Mockito.when(hotelModel.getId()).thenReturn(1L);

        Hotel hotelEntity = mock(Hotel.class);

        Mockito.when(hotelRepository.findById(ArgumentMatchers.any(Long.class)))
               .thenReturn(Optional.ofNullable(hotelEntity));
        Mockito.when(hotelModelMapper.toModel(ArgumentMatchers.any(Hotel.class))).thenReturn(hotelModel);

        HotelModel savedHotel = hotelService.findById(1L);

        Assertions.assertEquals(1L, savedHotel.getId());
    }

    @Test
    public void findById_Not_Found() {
        Mockito.when(hotelRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(HotelNotFoundException.class, () -> hotelService.findById(1L), "Hotel not found");
    }

    @Test
    public void update_Not_Found() {
        Mockito.when(hotelRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(HotelNotFoundException.class, () -> hotelService
                .update(1L, mock(HotelModel.class)), "Hotel not found");
    }

    @Test
    public void update() {
        HotelModel hotelModel = mock(HotelModel.class);
        Mockito.when(hotelModel.getId()).thenReturn(100L);

        Hotel hotelEntity = mock(Hotel.class);

        Mockito.when(hotelRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(hotelEntity));
        Mockito.when(hotelModelMapper.toEntity(ArgumentMatchers.any(HotelModel.class))).thenReturn(hotelEntity);
        Mockito.when(hotelRepository.save(ArgumentMatchers.any(Hotel.class))).thenReturn(hotelEntity);
        Mockito.when(hotelModelMapper.toModel(ArgumentMatchers.any(Hotel.class))).thenReturn(hotelModel);

        HotelModel update = hotelService.update(100L, new HotelModel());

        Assertions.assertEquals(100L, update.getId());

        verify(hotelRepository).findById(any(Long.class));
        verify(hotelRepository).save(any(Hotel.class));
        verify(hotelModelMapper).toEntity(any(HotelModel.class));
        verify(hotelModelMapper).toModel(any(Hotel.class));
    }

    @Test
    public void delete() {
        Hotel hotelEntity = mock(Hotel.class);

        Mockito.when(hotelRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(hotelEntity));

        boolean delete = hotelService.delete(1L);

        Assertions.assertTrue(delete);

        verify(hotelRepository).findById(any(Long.class));
        verify(hotelRepository).delete(any(Hotel.class));
    }

    @Test
    public void delete_No_Content() {
        Mockito.when(hotelRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

        boolean delete = hotelService.delete(1L);

        Assertions.assertFalse(delete);

        verify(hotelRepository).findById(any(Long.class));
        verify(hotelRepository, times(0)).delete(any(Hotel.class));
    }

    @Test
    public void findAll() {
        List<Hotel> entities = new ArrayList<>();
        entities.add(mock(Hotel.class));
        List<Hotel> entitiesSpy = spy(entities);
        Mockito.when(hotelRepository.findAll()).thenReturn(entitiesSpy);

        List<HotelModel> models = new ArrayList<>();
        HotelModel hotelModel = mock(HotelModel.class);
        models.add(hotelModel);
        List<HotelModel> modelsSpy = spy(models);
        Mockito.when(hotelModelMapper.toModels(entitiesSpy)).thenReturn(modelsSpy);

        List<HotelModel> all = hotelService.findAll(null);

        Assertions.assertNotNull(all);

        verify(hotelRepository).findAll();
    }

    @Test
    public void findAll_empty() {
        List<Hotel> entities = new ArrayList<>();
        Mockito.when(hotelRepository.findAll()).thenReturn(entities);

        List<HotelModel> all = hotelService.findAll(null);

        Assertions.assertNotNull(all);
        Assertions.assertTrue(all.isEmpty());

        verify(hotelRepository).findAll();
    }

    @Test
    public void findAll_filter_city() {
        List<Hotel> entities = new ArrayList<>();
        entities.add(mock(Hotel.class));
        List<Hotel> entitiesSpy = spy(entities);
        Mockito.when(hotelRepository.findByAddress_CityContaining(anyString())).thenReturn(entitiesSpy);

        List<HotelModel> models = new ArrayList<>();
        HotelModel hotelModel = mock(HotelModel.class);
        models.add(hotelModel);
        List<HotelModel> modelsSpy = spy(models);
        Mockito.when(hotelModelMapper.toModels(entitiesSpy)).thenReturn(modelsSpy);

        List<HotelModel> all = hotelService.findAll("hyderabad");

        Assertions.assertNotNull(all);

        verify(hotelRepository).findByAddress_CityContaining(anyString());
    }

}