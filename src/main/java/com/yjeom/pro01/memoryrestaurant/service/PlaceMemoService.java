package com.yjeom.pro01.memoryrestaurant.service;

import com.yjeom.pro01.memoryrestaurant.domain.Member;
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
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceMemoService {

    private final MemoImgService memoImgService;
    private final PlaceMemoRepository placeMemoRepository;
    private final PlaceRepository placeRepository;

    public PlaceMemoDto savePlaceMemo(HashMap<String, Object> memoMap, HashMap<String, Object> placeMap,
                                      MultipartFile file, Member member)throws Exception{
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
        PlaceMemo placeMemo;
        if(file !=null){
            memoImg=  memoImgService.saveMemoImg(file);
            placeMemo= PlaceMemo.builder()
                    .place(place)
                    .memoImg(memoImg)
                    .rating(Double.parseDouble(memoMap.get("rating").toString()))
                    .content(memoMap.get("content").toString())
                    .member(member)
                    .build();
        }else{
            placeMemo= PlaceMemo.builder()
                    .place(place)
                    .rating(Double.parseDouble(memoMap.get("rating").toString()))
                    .content(memoMap.get("content").toString())
                    .member(member)
                    .build();
        }
        PlaceMemo savedPlaceMemo=placeMemoRepository.save(placeMemo);
        PlaceMemoDto placeMemoDto;
        if(file !=null){
            placeMemoDto=PlaceMemoDto.builder()
                    .place(savedPlaceMemo.getPlace())
                    .memoImg(savedPlaceMemo.getMemoImg())
                    .placeMemo(savedPlaceMemo)
                    .member(savedPlaceMemo.getMember())
                    .build();
        }else{
            placeMemoDto=PlaceMemoDto.builder()
                    .place(savedPlaceMemo.getPlace())
                    .placeMemo(savedPlaceMemo)
                    .member(savedPlaceMemo.getMember())
                    .build();
        }

        return placeMemoDto;

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
                    .member(placeMemo.getMember())
                    .build();

            placeMemoDtos.add(placeMemoDto);
        }
        return placeMemoDtos;
    }

    public PlaceMemo updatePlaceMemo(HashMap<String, Object> memoMap,
                                      MultipartFile file)throws Exception{
        Optional<PlaceMemo> placeMemo=placeMemoRepository.findById(Long.parseLong(memoMap.get("memoId").toString()));
        if(placeMemo.isPresent()){
            PlaceMemo newPlaceMemo=placeMemo.get();
            MemoImg memoImg=memoImgService.updateMemoImg(newPlaceMemo,file!=null?file:null);
            if(memoImg !=null){
                newPlaceMemo.setMemoImg(memoImg);
            }
            newPlaceMemo.setContent(memoMap.get("content").toString());
            newPlaceMemo.setRating(Double.parseDouble(memoMap.get("rating").toString()));
            return newPlaceMemo;
        }
        return null;
    }

    public void deletePlaceMemo(Long memoId) throws  Exception{
        PlaceMemo placeMemo=placeMemoRepository.findById(memoId).get();
        if(placeMemo !=null){
            memoImgService.deleteMemoImg(placeMemo);
            placeMemoRepository.delete(placeMemo);
        }
    }
}
