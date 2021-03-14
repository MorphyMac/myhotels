package com.epam.myhotels.reservationservice.controller;

import com.epam.myhotels.reservationservice.dto.StayHistoryCollection;
import com.epam.myhotels.reservationservice.dto.StayHistoryDto;
import com.epam.myhotels.reservationservice.dto.mapper.StayHistoryDtoMapper;
import com.epam.myhotels.reservationservice.service.StayHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.net.URI;

@RestController
@Slf4j
@RequestMapping("/stayHistories")
public class StayHistoryController {

    @Autowired
    private StayHistoryService stayHistoryService;
    @Autowired
    private StayHistoryDtoMapper stayHistoryDtoMapper;

    @PostMapping
    public ResponseEntity<StayHistoryDto> createStayHistory(@Valid @RequestBody StayHistoryDto stayHistoryDto) {
        StayHistoryDto saved = stayHistoryDtoMapper
                .toDto(stayHistoryService.save(stayHistoryDtoMapper.toModel(stayHistoryDto)));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId())
                                                  .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StayHistoryDto> getStayHistoryByID(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(stayHistoryDtoMapper.toDto(stayHistoryService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StayHistoryDto> updateStayHistory(@PathVariable @Min(1) Long id,
                                                            @RequestBody StayHistoryDto stayHistory) {
        return ResponseEntity.ok(stayHistoryDtoMapper
                .toDto(stayHistoryService.update(id, stayHistoryDtoMapper.toModel(stayHistory))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStayHistory(@PathVariable @Min(1) Long id) {
        return stayHistoryService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<StayHistoryCollection> getAllStayHistories() {
        return ResponseEntity.ok(new StayHistoryCollection(stayHistoryDtoMapper.toDto(stayHistoryService.findAll())));
    }

}