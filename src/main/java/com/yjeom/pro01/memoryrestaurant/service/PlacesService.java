package com.yjeom.pro01.memoryrestaurant.service;

import com.yjeom.pro01.memoryrestaurant.domain.Places;
import com.yjeom.pro01.memoryrestaurant.domain.PlacesRepository;
import com.yjeom.pro01.memoryrestaurant.dto.PlacesSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlacesService {
    private final PlacesRepository placesRepository;

    @Transactional
    public long save(PlacesSaveRequestDto requestDto){
        return placesRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public List<Places> getList(HashMap<String,Double> data){
        return placesRepository.findALLDesc(data.get("sw_x"),data.get("sw_y")
        ,data.get("ne_x"),data.get("ne_y"));
    }

    @Transactional
    public Places get(double position_x,double position_y){
        return placesRepository.findByPosition(position_x,position_y);
    }
}
