package com.yjeom.pro01.memoryrestaurant.controller;
import com.yjeom.pro01.memoryrestaurant.domain.MemoImg;
import com.yjeom.pro01.memoryrestaurant.domain.Place;
import com.yjeom.pro01.memoryrestaurant.dto.PlaceMemoDto;
import com.yjeom.pro01.memoryrestaurant.service.MemoImgService;
import com.yjeom.pro01.memoryrestaurant.service.PlaceMemoService;
import com.yjeom.pro01.memoryrestaurant.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class PlaceMemoApiController {

    private final MemoImgService memoImgService;
    private final PlaceService placeService;
    private final PlaceMemoService placeMemoService;

    @GetMapping("/")
    public ModelAndView index(){
        ModelAndView model=new ModelAndView("index");
        return model;

    }
    @PostMapping("/placeMemo")
    public void addPlaceMemo(@RequestPart(value = "memo",required = false) HashMap<String, Object> memo,
                     @RequestPart(value = "place",required = false) HashMap<String, Object> place,
                     @RequestPart(value="imgFile", required=false) MultipartFile file) throws Exception {
        System.out.println(place);
        placeMemoService.savePlaceMemo(memo,place,file);

    }


    @GetMapping("/placeMemoList/{placeApiId}/{page}")
    public  @ResponseBody Page<PlaceMemoDto> getPlaceMemoList(@PathVariable Long placeApiId,
                                                              @PathVariable("page") Optional<Integer> page
                                                              ){
        Pageable pageable= PageRequest.of(page.isPresent()?page.get():0,4);

        return placeMemoService.getPlaceMemoList(placeApiId,pageable);
    }
//
//    @ResponseBody
//    @PostMapping("/")
//    public List<Place> index(@RequestBody(required = false) HashMap<String,Double> data){
//        List<Place> placeList = placeService.getList(data);
//        return placeList;
//
//    }
//
//    @PostMapping("/api/v1/places")
//    public Long save(@RequestBody PlacesSaveRequestDto requestDto, Principal principal){
//        String email=principal.getName();
//        return placeService.save(requestDto,email);
//    }
//
//    @ResponseBody
//    @GetMapping  ("/api/v1/{positionX}/{positionY}/{page}")
//    public Page<Place> get(@PathVariable double positionX, @PathVariable double positionY
//            , @PathVariable Optional<Integer> page){
//        Pageable pageable= PageRequest.of(page.isPresent()?page.get():0,3);
//
//        return placeService.getList(positionX,positionY,pageable);
//    }
//
//    @GetMapping("/api/v1/places/{id}")
//    public Place getPlace(@PathVariable Long id){
//        return placeService.getPlace(id);
//    }
//
//    @PutMapping("api/v1/places/{id}")
//    public Place update(@PathVariable Long id, @RequestBody PlacesUpdateRequestDto requestDto){
//        return placeService.update(id,requestDto);
//    }
//
//    @DeleteMapping("api/v1/places/{id}")
//    public Long delete(@PathVariable Long id){
//        placeService.delete(id);
//        return id;
//    }
}
