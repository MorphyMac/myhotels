package com.epam.myhotels.reservationservice.service;


import com.epam.myhotels.reservationservice.entity.Reservation;
import com.epam.myhotels.reservationservice.exception.ReservationNotFoundException;
import com.epam.myhotels.reservationservice.model.ReservationModel;
import com.epam.myhotels.reservationservice.model.mapper.ReservationModelMapper;
import com.epam.myhotels.reservationservice.repository.ReservationRepository;
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
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ReservationModelMapper reservationModelMapper;

    private ReservationService reservationService;

    @BeforeEach
    public void setUp() {
        this.reservationService = new ReservationService();
        this.reservationService.setReservationRepository(reservationRepository);
        this.reservationService.setReservationModelMapper(reservationModelMapper);
    }

    @Test
    public void save() {
        Reservation reservationEntity = mock(Reservation.class);
        ReservationModel reservationModel = mock(ReservationModel.class);
        Mockito.when(reservationModel.getId()).thenReturn(1L);
        Mockito.when(reservationModelMapper.toEntity(ArgumentMatchers.any(ReservationModel.class)))
               .thenReturn(reservationEntity);
        Mockito.when(reservationModelMapper.toModel(ArgumentMatchers.any(Reservation.class)))
               .thenReturn(reservationModel);
        Mockito.when(reservationRepository.save(ArgumentMatchers.any(Reservation.class))).thenReturn(reservationEntity);

        ReservationModel savedReservation = reservationService.save(reservationModel);

        Assertions.assertEquals(1L, savedReservation.getId());

        verify(reservationModelMapper).toEntity(reservationModel);
        verify(reservationModelMapper).toModel(reservationEntity);
        verify(reservationRepository).save(reservationEntity);
    }

    @Test
    public void findById() {
        ReservationModel reservationModel = mock(ReservationModel.class);
        Mockito.when(reservationModel.getId()).thenReturn(1L);

        Reservation reservationEntity = mock(Reservation.class);

        Mockito.when(reservationRepository.findById(ArgumentMatchers.any(Long.class)))
               .thenReturn(Optional.ofNullable(reservationEntity));
        Mockito.when(reservationModelMapper.toModel(ArgumentMatchers.any(Reservation.class)))
               .thenReturn(reservationModel);

        ReservationModel savedReservation = reservationService.findById(1L);

        Assertions.assertEquals(1L, savedReservation.getId());
    }

    @Test
    public void findById_Not_Found() {
        Mockito.when(reservationRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(ReservationNotFoundException.class, () -> reservationService
                .findById(1L), "Reservation not found");
    }

    @Test
    public void update_Not_Found() {
        Mockito.when(reservationRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(ReservationNotFoundException.class, () -> reservationService
                .update(1L, mock(ReservationModel.class)), "Reservation not found");
    }

    @Test
    public void update() {
        ReservationModel reservationModel = mock(ReservationModel.class);
        Mockito.when(reservationModel.getId()).thenReturn(100L);

        Reservation reservationEntity = mock(Reservation.class);

        Mockito.when(reservationRepository.findById(ArgumentMatchers.any(Long.class)))
               .thenReturn(Optional.of(reservationEntity));
        Mockito.when(reservationModelMapper.toEntity(ArgumentMatchers.any(ReservationModel.class)))
               .thenReturn(reservationEntity);
        Mockito.when(reservationRepository.save(ArgumentMatchers.any(Reservation.class))).thenReturn(reservationEntity);
        Mockito.when(reservationModelMapper.toModel(ArgumentMatchers.any(Reservation.class)))
               .thenReturn(reservationModel);

        ReservationModel update = reservationService.update(100L, new ReservationModel());

        Assertions.assertEquals(100L, update.getId());

        verify(reservationRepository).findById(any(Long.class));
        verify(reservationRepository).save(any(Reservation.class));
        verify(reservationModelMapper).toEntity(any(ReservationModel.class));
        verify(reservationModelMapper).toModel(any(Reservation.class));
    }

    @Test
    public void delete() {
        Reservation reservationEntity = mock(Reservation.class);

        Mockito.when(reservationRepository.findById(ArgumentMatchers.any(Long.class)))
               .thenReturn(Optional.of(reservationEntity));

        boolean delete = reservationService.delete(1L);

        Assertions.assertTrue(delete);

        verify(reservationRepository).findById(any(Long.class));
        verify(reservationRepository).delete(any(Reservation.class));
    }

    @Test
    public void delete_No_Content() {
        Mockito.when(reservationRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

        boolean delete = reservationService.delete(1L);

        Assertions.assertFalse(delete);

        verify(reservationRepository).findById(any(Long.class));
        verify(reservationRepository, times(0)).delete(any(Reservation.class));
    }

    @Test
    public void findAll() {
        List<Reservation> entities = new ArrayList<>();
        entities.add(mock(Reservation.class));
        List<Reservation> entitiesSpy = spy(entities);
        Mockito.when(reservationRepository.findAll()).thenReturn(entitiesSpy);

        List<ReservationModel> models = new ArrayList<>();
        ReservationModel reservationModel = mock(ReservationModel.class);
        models.add(reservationModel);
        List<ReservationModel> modelsSpy = spy(models);
        Mockito.when(reservationModelMapper.toModels(entitiesSpy)).thenReturn(modelsSpy);

        List<ReservationModel> all = reservationService.findAll();

        Assertions.assertNotNull(all);

        verify(reservationRepository).findAll();
    }

    @Test
    public void findAll_empty() {
        List<Reservation> entities = new ArrayList<>();
        Mockito.when(reservationRepository.findAll()).thenReturn(entities);

        List<ReservationModel> all = reservationService.findAll();

        Assertions.assertNotNull(all);
        Assertions.assertTrue(all.isEmpty());

        verify(reservationRepository).findAll();
    }

    @Test
    public void findByGuestId() {
        List<Reservation> entities = new ArrayList<>();
        entities.add(mock(Reservation.class));
        List<Reservation> entitiesSpy = spy(entities);
        Mockito.when(reservationRepository.findByGuestId(anyString())).thenReturn(entitiesSpy);

        List<ReservationModel> models = new ArrayList<>();
        ReservationModel reservationModel = mock(ReservationModel.class);
        models.add(reservationModel);
        List<ReservationModel> modelsSpy = spy(models);
        Mockito.when(reservationModelMapper.toModels(entitiesSpy)).thenReturn(modelsSpy);

        List<ReservationModel> all = reservationService.findByGuestId("123");

        Assertions.assertNotNull(all);

        verify(reservationRepository).findByGuestId(anyString());
    }

    @Test
    public void findByGuestId_Not_Found() {
        List<Reservation> entities = new ArrayList<>();
        Mockito.when(reservationRepository.findByGuestId(anyString())).thenReturn(entities);

        List<ReservationModel> all = reservationService.findByGuestId("1234");

        Assertions.assertNotNull(all);
        Assertions.assertTrue(all.isEmpty());

        verify(reservationRepository).findByGuestId(anyString());
    }

}