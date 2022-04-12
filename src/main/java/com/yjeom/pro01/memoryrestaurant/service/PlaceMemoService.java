package com.yjeom.pro01.memoryrestaurant.service;

import com.yjeom.pro01.memoryrestaurant.domain.MemoImg;
import com.yjeom.pro01.memoryrestaurant.domain.Place;
import com.yjeom.pro01.memoryrestaurant.domain.PlaceMemo;
import com.yjeom.pro01.memoryrestaurant.repository.PlaceMemoRepository;
import com.yjeom.pro01.memoryrestaurant.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceMemoService {

    private final MemoImgService memoImgService;
    private final PlaceMemoRepository placeMemoRepository;
    private final PlaceRepository placeRepository;

    public Long savePlaceMemo(HashMap<String, Object> memo, HashMap<String, Object> place,
                              MultipartFile file)throws Exception{
        Place p=Place.createPlace(place.get("place_name").toString(),
                Double.parseDouble(place.get("x").toString()),
                Double.parseDouble(place.get("y").toString()));
        placeRepository.save(p);
        MemoImg memoImg=new MemoImg();
        memoImgService.saveMemoImg(memoImg,file);
        PlaceMemo placeMemo= PlaceMemo.createPlaceMemo(p,memoImg,
                Double.parseDouble(memo.get("rating").toString()),memo.get("content").toString());

        placeMemoRepository.save(placeMemo);
        return placeMemo.getId();

    }
}
