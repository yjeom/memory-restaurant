package com.yjeom.pro01.memoryrestaurant.service;

import com.yjeom.pro01.memoryrestaurant.domain.Member;
import com.yjeom.pro01.memoryrestaurant.domain.Place;
import com.yjeom.pro01.memoryrestaurant.repository.MemberRepository;
import com.yjeom.pro01.memoryrestaurant.repository.PlaceRepository;
import com.yjeom.pro01.memoryrestaurant.dto.PlacesSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;

//    @Transactional
//    public long save(PlacesSaveRequestDto requestDto,String email){
//        Member member=memberRepository.findByEmail(email);
//        requestDto.setMember(member);
////        return placeRepository.save(requestDto.toEntity()).getId();
//    }

    @Transactional(readOnly = true)
    public List<Place> getList(HashMap<String,Double> data){
        List<Place> list= placeRepository.getBoundPlaceList(data.get("sw_x"),data.get("sw_y")
                ,data.get("ne_x"),data.get("ne_y"));
        List<String> duplication=new ArrayList<>();
        if(!list.isEmpty()){
            for(int i=0;i<list.size();i++){
                if(duplication.contains(list.get(i).getPlaceName())){
                    list.remove(i);
                }else{
                    duplication.add(list.get(i).getPlaceName());
                }
            }
        }
        return list;
    }

    @Transactional(readOnly = true)
    public Page<Place> getList(double positionX, double positionY, Pageable pageable){
        return placeRepository.findByPositionXAndPositionYOrderByIdDesc(positionX,positionY,pageable);
    }

    @Transactional(readOnly = true)
    public Place getPlace(Long id){
        Place place = placeRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 내역이 존재하지 않습니다."));
        return place;
    }

    @Transactional
    public Long delete(Long id){
        Place place = placeRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 내역이 존재하지 않습니다."));
        placeRepository.delete(place);
        return id;
    }
}
