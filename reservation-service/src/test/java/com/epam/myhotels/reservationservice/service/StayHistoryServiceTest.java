package com.epam.myhotels.reservationservice.service;

import com.epam.myhotels.reservationservice.entity.StayHistory;
import com.epam.myhotels.reservationservice.exception.StayHistoryNotFoundException;
import com.epam.myhotels.reservationservice.model.StayHistoryModel;
import com.epam.myhotels.reservationservice.model.mapper.StayHistoryModelMapper;
import com.epam.myhotels.reservationservice.repository.StayHistoryRepository;
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
public class StayHistoryServiceTest {

    @Mock
    private StayHistoryRepository stayHistoryRepository;
    @Mock
    private StayHistoryModelMapper stayHistoryModelMapper;

    private StayHistoryService stayHistoryService;

    @BeforeEach
    public void setUp() {
        this.stayHistoryService = new StayHistoryService();
        this.stayHistoryService.setStayHistoryRepository(stayHistoryRepository);
        this.stayHistoryService.setStayHistoryModelMapper(stayHistoryModelMapper);
    }


    @Test
    public void save() {
        StayHistoryModel stayHistoryModel = mock(StayHistoryModel.class);
        Mockito.when(stayHistoryModel.getId()).thenReturn(1L);
        Mockito.when(stayHistoryModel.getGuestId()).thenReturn(1L);
        Mockito.when(stayHistoryModel.getReservationId()).thenReturn(1L);

        StayHistory stayHistoryEntity = mock(StayHistory.class);

        Mockito.when(stayHistoryModelMapper.toEntity(ArgumentMatchers.any(StayHistoryModel.class)))
               .thenReturn(stayHistoryEntity);
        Mockito.when(stayHistoryModelMapper.toModel(ArgumentMatchers.any(StayHistory.class)))
               .thenReturn(stayHistoryModel);
        Mockito.when(stayHistoryRepository.save(ArgumentMatchers.any(StayHistory.class))).thenReturn(stayHistoryEntity);

        StayHistoryModel savedStayHistory = stayHistoryService.save(new StayHistoryModel());

        Assertions.assertEquals(1L, savedStayHistory.getId());
        Assertions.assertEquals(1L, savedStayHistory.getGuestId());
        Assertions.assertEquals(1L, savedStayHistory.getReservationId());
    }

    @Test
    public void findById() {
        StayHistoryModel stayHistoryModel = mock(StayHistoryModel.class);
        Mockito.when(stayHistoryModel.getId()).thenReturn(1L);
        Mockito.when(stayHistoryModel.getGuestId()).thenReturn(1L);
        Mockito.when(stayHistoryModel.getReservationId()).thenReturn(1L);

        StayHistory stayHistoryEntity = mock(StayHistory.class);

        Mockito.when(stayHistoryRepository.findById(ArgumentMatchers.any(Long.class)))
               .thenReturn(Optional.ofNullable(stayHistoryEntity));
        Mockito.when(stayHistoryModelMapper.toModel(ArgumentMatchers.any(StayHistory.class)))
               .thenReturn(stayHistoryModel);

        StayHistoryModel savedStayHistory = stayHistoryService.findById(1L);

        Assertions.assertEquals(1L, savedStayHistory.getId());
        Assertions.assertEquals(1L, savedStayHistory.getGuestId());
        Assertions.assertEquals(1, savedStayHistory.getReservationId());
    }

    @Test
    public void findById_Not_Found() {
        Mockito.when(stayHistoryRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(StayHistoryNotFoundException.class, () -> stayHistoryService
                .findById(1L), "StayHistory not found");
    }

    @Test
    public void update_Not_Found() {
        Mockito.when(stayHistoryRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(StayHistoryNotFoundException.class, () -> stayHistoryService
                .update(1L, mock(StayHistoryModel.class)), "StayHistory not found");
    }

    @Test
    public void update() {
        StayHistoryModel stayHistoryModel = mock(StayHistoryModel.class);
        Mockito.when(stayHistoryModel.getId()).thenReturn(100L);
        Mockito.when(stayHistoryModel.getGuestId()).thenReturn(1L);
        Mockito.when(stayHistoryModel.getReservationId()).thenReturn(1L);

        StayHistory stayHistoryEntity = mock(StayHistory.class);

        Mockito.when(stayHistoryRepository.findById(ArgumentMatchers.any(Long.class)))
               .thenReturn(Optional.of(stayHistoryEntity));
        Mockito.when(stayHistoryModelMapper.toEntity(ArgumentMatchers.any(StayHistoryModel.class)))
               .thenReturn(stayHistoryEntity);
        Mockito.when(stayHistoryRepository.save(ArgumentMatchers.any(StayHistory.class))).thenReturn(stayHistoryEntity);
        Mockito.when(stayHistoryModelMapper.toModel(ArgumentMatchers.any(StayHistory.class)))
               .thenReturn(stayHistoryModel);

        StayHistoryModel update = stayHistoryService.update(100L, new StayHistoryModel());

        Assertions.assertEquals(100L, update.getId());
        Assertions.assertEquals(1L, update.getGuestId());
        Assertions.assertEquals(1, update.getReservationId());

        verify(stayHistoryRepository).findById(any(Long.class));
        verify(stayHistoryRepository).save(any(StayHistory.class));
        verify(stayHistoryModelMapper).toEntity(any(StayHistoryModel.class));
        verify(stayHistoryModelMapper).toModel(any(StayHistory.class));
    }

    @Test
    public void delete() {
        StayHistory stayHistoryEntity = mock(StayHistory.class);

        Mockito.when(stayHistoryRepository.findById(ArgumentMatchers.any(Long.class)))
               .thenReturn(Optional.of(stayHistoryEntity));

        boolean delete = stayHistoryService.delete(1L);

        Assertions.assertTrue(delete);

        verify(stayHistoryRepository).findById(any(Long.class));
        verify(stayHistoryRepository).delete(any(StayHistory.class));
    }

    @Test
    public void delete_No_Content() {
        Mockito.when(stayHistoryRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

        boolean delete = stayHistoryService.delete(1L);

        Assertions.assertFalse(delete);

        verify(stayHistoryRepository).findById(any(Long.class));
        verify(stayHistoryRepository, times(0)).delete(any(StayHistory.class));
    }

    @Test
    public void findAll() {
        List<StayHistory> entities = new ArrayList<>();
        entities.add(mock(StayHistory.class));
        List<StayHistory> entitiesSpy = spy(entities);
        Mockito.when(stayHistoryRepository.findAll()).thenReturn(entitiesSpy);

        List<StayHistoryModel> models = new ArrayList<>();
        StayHistoryModel stayHistoryModel = mock(StayHistoryModel.class);
        models.add(stayHistoryModel);
        List<StayHistoryModel> modelsSpy = spy(models);
        Mockito.when(stayHistoryModelMapper.toModels(entitiesSpy)).thenReturn(modelsSpy);

        List<StayHistoryModel> all = stayHistoryService.findAll();

        Assertions.assertNotNull(all);

        verify(stayHistoryRepository).findAll();
    }

    @Test
    public void findAll_empty() {
        List<StayHistory> entities = new ArrayList<>();
        Mockito.when(stayHistoryRepository.findAll()).thenReturn(entities);

        List<StayHistoryModel> all = stayHistoryService.findAll();

        Assertions.assertNotNull(all);
        Assertions.assertTrue(all.isEmpty());

        verify(stayHistoryRepository).findAll();
    }

}