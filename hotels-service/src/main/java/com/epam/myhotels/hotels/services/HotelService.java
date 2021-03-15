package com.epam.myhotels.hotels.services;


import com.epam.myhotels.hotels.entity.Hotel;
import com.epam.myhotels.hotels.exception.HotelNotFoundException;
import com.epam.myhotels.hotels.model.HotelModel;
import com.epam.myhotels.hotels.model.mapper.HotelModelMapper;
import com.epam.myhotels.hotels.repository.HotelRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@Setter
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HotelModelMapper hotelModelMapper;

    @Transactional
    public HotelModel save(HotelModel hotelModel) {
        return hotelModelMapper.toModel(hotelRepository.save(hotelModelMapper.toEntity(hotelModel)));
    }

    @Transactional(readOnly = true)
    public HotelModel findById(Long id) {
        return hotelRepository.findById(id).map(hotel -> hotelModelMapper.toModel(hotel))
                              .orElseThrow(() -> new HotelNotFoundException("Hotel not found"));
    }

    @Transactional
    public HotelModel update(Long id, HotelModel hotel) {
        return hotelRepository.findById(id).map(existingHotel -> {
            hotel.setId(existingHotel.getId());
            Hotel update = hotelModelMapper.toEntity(hotel);
            return hotelModelMapper.toModel(hotelRepository.save(update));
        }).orElseThrow(() -> new HotelNotFoundException("Hotel not found"));
    }

    @Transactional
    public boolean delete(Long id) {
        return hotelRepository.findById(id).map(existingHotel -> {
            hotelRepository.delete(existingHotel);
            return true;
        }).orElse(false);
    }

    @Transactional(readOnly = true)
    public List<HotelModel> findAll(String city) {
        List<Hotel> hotels = StringUtils.isEmpty(city) ? hotelRepository.findAll() : hotelRepository
                .findByAddress_CityContaining(city);
        if (CollectionUtils.isEmpty(hotels)) {
            return Collections.emptyList();
        } else {
            return hotelModelMapper.toModels(hotels);
        }
    }
}