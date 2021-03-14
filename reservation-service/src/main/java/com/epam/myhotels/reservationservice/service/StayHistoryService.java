package com.epam.myhotels.reservationservice.service;


import com.epam.myhotels.reservationservice.entity.StayHistory;
import com.epam.myhotels.reservationservice.exception.StayHistoryNotFoundException;
import com.epam.myhotels.reservationservice.model.StayHistoryModel;
import com.epam.myhotels.reservationservice.model.mapper.StayHistoryModelMapper;
import com.epam.myhotels.reservationservice.repository.StayHistoryRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@Setter
public class StayHistoryService {

    @Autowired
    private StayHistoryRepository stayHistoryRepository;
    @Autowired
    private StayHistoryModelMapper stayHistoryModelMapper;

    @Transactional
    public StayHistoryModel save(StayHistoryModel stayHistoryModel) {
        return stayHistoryModelMapper
                .toModel(stayHistoryRepository.save(stayHistoryModelMapper.toEntity(stayHistoryModel)));
    }

    @Transactional(readOnly = true)
    public StayHistoryModel findById(Long id) {
        return stayHistoryRepository.findById(id).map(stayHistory -> stayHistoryModelMapper.toModel(stayHistory))
                                    .orElseThrow(() -> new StayHistoryNotFoundException("StayHistory not found"));
    }

    @Transactional
    public StayHistoryModel update(Long id, StayHistoryModel stayHistory) {
        return stayHistoryRepository.findById(id).map(existingStayHistory -> {
            StayHistory update = stayHistoryModelMapper.toEntity(stayHistory);
            update.setId(existingStayHistory.getId());
            return stayHistoryModelMapper.toModel(stayHistoryRepository.save(update));
        }).orElseThrow(() -> new StayHistoryNotFoundException("StayHistory not found"));
    }

    @Transactional
    public boolean delete(Long id) {
        return stayHistoryRepository.findById(id).map(existingStayHistory -> {
            stayHistoryRepository.delete(existingStayHistory);
            return true;
        }).orElse(false);
    }

    @Transactional(readOnly = true)
    public List<StayHistoryModel> findAll() {
        List<StayHistory> stayHistorys = stayHistoryRepository.findAll();
        if (CollectionUtils.isEmpty(stayHistorys)) {
            return Collections.emptyList();
        } else {
            return stayHistoryModelMapper.toModels(stayHistorys);
        }
    }
}