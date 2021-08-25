package com.yjeom.pro01.memoryrestaurant.service;

import com.yjeom.pro01.memoryrestaurant.domain.PlacesRepository;
import com.yjeom.pro01.memoryrestaurant.dto.PlacesSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PlacesService {
    private final PlacesRepository placesRepository;

    @Transactional
    public long save(PlacesSaveRequestDto requestDto){
        return placesRepository.save(requestDto.toEntity()).getId();
    }
}
