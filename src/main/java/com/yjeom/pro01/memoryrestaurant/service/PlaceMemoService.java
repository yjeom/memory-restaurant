package com.yjeom.pro01.memoryrestaurant.service;

import com.yjeom.pro01.memoryrestaurant.domain.MemoImg;
import com.yjeom.pro01.memoryrestaurant.domain.Place;
import com.yjeom.pro01.memoryrestaurant.domain.PlaceMemo;
import com.yjeom.pro01.memoryrestaurant.dto.PlaceMemoDto;
import com.yjeom.pro01.memoryrestaurant.repository.PlaceMemoRepository;
import com.yjeom.pro01.memoryrestaurant.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceMemoService {

    private final MemoImgService memoImgService;
    private final PlaceMemoRepository placeMemoRepository;
    private final PlaceRepository placeRepository;

    public Long savePlaceMemo(HashMap<String, Object> memoMap, HashMap<String, Object> placeMap,
                              MultipartFile file)throws Exception{
        Place place=placeRepository.findByApiId(Long.parseLong(placeMap.get("id").toString()));
        if(place==null){
            place=Place.builder()
                    .placeName(placeMap.get("place_name").toString())
                    .apiId(Long.parseLong(placeMap.get("id").toString()))
                    .positionX(Double.parseDouble(placeMap.get("x").toString()))
                    .positionY(Double.parseDouble(placeMap.get("y").toString()))
                    .build();
            placeRepository.save(place);
        }
        MemoImg memoImg=new MemoImg();
        memoImgService.saveMemoImg(memoImg,file);
        PlaceMemo placeMemo= PlaceMemo.createPlaceMemo(place,memoImg,
                Double.parseDouble(memoMap.get("rating").toString()),memoMap.get("content").toString());

        placeMemoRepository.save(placeMemo);
        return placeMemo.getId();

    }

    @Transactional(readOnly = true)
    public List<PlaceMemoDto> getPlaceMemoList(Long placeApiId){
        Place place=placeRepository.findByApiId(placeApiId);
        if(place==null) return null;
        List<PlaceMemo> placeMemoList= placeMemoRepository.findByPlaceIdOrderByIdDesc(place.getId());
        List<PlaceMemoDto> placeMemoDtos=new ArrayList<>();
        for(PlaceMemo placeMemo: placeMemoList){
            MemoImg memoImg= placeMemo.getMemoImg();
            PlaceMemoDto placeMemoDto=PlaceMemoDto.builder()
                    .placeMemo(placeMemo)
                    .memoImg(memoImg)
                    .place(place)
                    .build();

            placeMemoDtos.add(placeMemoDto);
        }
        return placeMemoDtos;
    }
}
