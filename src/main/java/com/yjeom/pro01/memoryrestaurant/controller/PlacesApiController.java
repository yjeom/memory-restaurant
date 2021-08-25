package com.yjeom.pro01.memoryrestaurant.controller;

import com.yjeom.pro01.memoryrestaurant.dto.PlacesSaveRequestDto;
import com.yjeom.pro01.memoryrestaurant.service.PlacesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PlacesApiController {

    private final PlacesService placesService;

    @PostMapping("/api/v1/places")
    public Long save(@RequestBody PlacesSaveRequestDto requestDto){
        return placesService.save(requestDto);
    }
}
