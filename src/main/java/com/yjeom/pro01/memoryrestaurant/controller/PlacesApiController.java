package com.yjeom.pro01.memoryrestaurant.controller;
import com.yjeom.pro01.memoryrestaurant.domain.Places;
import com.yjeom.pro01.memoryrestaurant.dto.PlacesSaveRequestDto;
import com.yjeom.pro01.memoryrestaurant.dto.PlacesUpdateRequestDto;
import com.yjeom.pro01.memoryrestaurant.service.PlacesService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class PlacesApiController {

    private final PlacesService placesService;

    @GetMapping("/")
    public ModelAndView index(){
        ModelAndView model=new ModelAndView("index");
        return model;

    }

    @ResponseBody
    @PostMapping("/")
    public List<Places> index(@RequestBody(required = false) HashMap<String,Double> data){
        List<Places> placesList=placesService.getList(data);
        return placesList;

    }

    @PostMapping("/api/v1/places")
    public Long save(@RequestBody PlacesSaveRequestDto requestDto, Principal principal){
        String email=principal.getName();
        return placesService.save(requestDto,email);
    }

    @ResponseBody
    @GetMapping  ("/api/v1/{positionX}/{positionY}/{page}")
    public Page<Places> get(@PathVariable double positionX, @PathVariable double positionY
            , @PathVariable Optional<Integer> page){
        Pageable pageable= PageRequest.of(page.isPresent()?page.get():0,3);

        return placesService.getList(positionX,positionY,pageable);
    }

    @PutMapping("api/v1/places/{id}")
    public Long update(@PathVariable Long id,@RequestBody PlacesUpdateRequestDto requestDto){
        return placesService.update(id,requestDto);
    }

    @DeleteMapping("api/v1/places/{id}")
    public Long delete(@PathVariable Long id){
        placesService.delete(id);
        return id;
    }
}
