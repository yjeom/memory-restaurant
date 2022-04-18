package com.yjeom.pro01.memoryrestaurant.controller;
import com.yjeom.pro01.memoryrestaurant.domain.Member;
import com.yjeom.pro01.memoryrestaurant.domain.PlaceMemo;
import com.yjeom.pro01.memoryrestaurant.dto.PlaceMemoDto;
import com.yjeom.pro01.memoryrestaurant.dto.ResponseDto;
import com.yjeom.pro01.memoryrestaurant.service.MemberService;
import com.yjeom.pro01.memoryrestaurant.service.MemoImgService;
import com.yjeom.pro01.memoryrestaurant.service.PlaceMemoService;
import com.yjeom.pro01.memoryrestaurant.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PlaceMemoApiController {

    private final MemoImgService memoImgService;
    private final PlaceService placeService;
    private final PlaceMemoService placeMemoService;
    private final MemberService memberService;

//    @GetMapping("/")
//    public ModelAndView index(){
//        ModelAndView model=new ModelAndView("index");
//        return model;
//
//    }
    @PostMapping("/placeMemo")
    public ResponseEntity<?> addPlaceMemo(@RequestPart(value = "memo",required = true) HashMap<String, Object> memo,
                                          @RequestPart(value = "place",required = true) HashMap<String, Object> place,
                                          @RequestPart(value="imgFile", required=false) MultipartFile reqFile)  {
        try {
            MultipartFile file;
            if(reqFile==null){
                file=null;
            }else{
                file=reqFile;
            }
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            Member member=memberService.getMember(email);
            PlaceMemoDto placeMemoDto=placeMemoService.savePlaceMemo(memo, place, file,member);
            return ResponseEntity.ok().body(placeMemoDto);
        }catch (Exception e){
            ResponseDto responseDto=ResponseDto.builder()
                    .error(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }

    }


    @GetMapping("/placeMemos/{placeApiId}")
    public  @ResponseBody
    List<PlaceMemoDto> getPlaceMemoList(@PathVariable Long placeApiId){

        return placeMemoService.getPlaceMemoList(placeApiId);
    }
    @PutMapping("/placeMemo")
    public ResponseEntity<?> updatePlaceMemo(@RequestPart(value = "memo",required = true) HashMap<String, Object> memo,
                                          @RequestPart(value="imgFile", required=false) MultipartFile reqFile) {
        try {

            PlaceMemo newPlaceMemo = placeMemoService.updatePlaceMemo(memo,reqFile);
            PlaceMemoDto placeMemoDto=PlaceMemoDto.builder()
                    .placeMemo(newPlaceMemo)
                    .place(newPlaceMemo.getPlace())
                    .memoImg(newPlaceMemo.getMemoImg())
                    .member(newPlaceMemo.getMember())
                    .build();
            return ResponseEntity.ok().body(placeMemoDto);
        } catch (Exception e) {
            ResponseDto responseDto = ResponseDto.builder()
                    .error(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @DeleteMapping(value = "/placeMemo")
    public ResponseEntity<?> deletePlaceMemo(@RequestBody HashMap<String, Object> memo) {
        try {
            placeMemoService.deletePlaceMemo(Long.parseLong(memo.get("memoId").toString()));
            return ResponseEntity.ok().body(Long.parseLong(memo.get("memoId").toString()));
        } catch (Exception e) {
            ResponseDto responseDto = ResponseDto.builder()
                    .error(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
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
