package com.yjeom.pro01.memoryrestaurant.service;

import com.yjeom.pro01.memoryrestaurant.domain.Places;
import com.yjeom.pro01.memoryrestaurant.repository.PlacesRepository;
import com.yjeom.pro01.memoryrestaurant.dto.PlacesSaveRequestDto;
import com.yjeom.pro01.memoryrestaurant.dto.PlacesUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlacesService {
    private final PlacesRepository placesRepository;

    @Transactional
    public long save(PlacesSaveRequestDto requestDto){
        return placesRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public List<Places> getList(HashMap<String,Double> data){
        List<Places> list=placesRepository.getBoundPlaceList(data.get("sw_x"),data.get("sw_y")
                ,data.get("ne_x"),data.get("ne_y"));
        List<String> duplication=new ArrayList<>();
        if(!list.isEmpty()){
            for(int i=0;i<list.size();i++){
                if(duplication.contains(list.get(i).getPlace_name())){
                    list.remove(i);
                }else{
                    duplication.add(list.get(i).getPlace_name());
                }
            }
        }
        return list;
    }

    @Transactional
    public Places get(double position_x,double position_y){
        return placesRepository.findByPosition(position_x,position_y);
    }

    @Transactional
    public Long update(Long id, PlacesUpdateRequestDto requestDto){
        Places places=placesRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 내역이 존재하지 않습니다."));
        places.update(requestDto.getContent());
        return id;
    }

    @Transactional
    public Long delete(Long id){
        Places places=placesRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 내역이 존재하지 않습니다."));
        placesRepository.delete(places);
        return id;
    }
}
