package com.yjeom.pro01.memoryrestaurant.controller;
import com.yjeom.pro01.memoryrestaurant.domain.Places;
import com.yjeom.pro01.memoryrestaurant.dto.PlacesSaveRequestDto;
import com.yjeom.pro01.memoryrestaurant.service.PlacesService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.HashMap;
import java.util.List;

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
//        System.out.println("통신 성공"+ data.get("sw_x"));
//
//        JSONArray jsonArr=new JSONArray();
//        JSONObject json=new JSONObject();
//        List<Places> placesList=placesService.getList(data);
//        for(int i=0;i<placesList.size();i++){
//            json.put("place_name",placesList.get(i).getPlace_name());
//            json.put("content",placesList.get(i).getContent());
//            json.put("position_x",placesList.get(i).getPosition_x());
//            json.put("position_y",placesList.get(i).getPosition_y());
//            jsonArr.put(json);
//        }

        System.out.println(data.get("sw_x")+" /"+ data.get("ne_x"));
        System.out.println(data.get("sw_y")+" /"+ data.get("ne_y"));
        List<Places> placesList=placesService.getList(data);
        System.out.println(placesList.size()+"결과 리스트 사이즈");
        return placesList;

    }

    @PostMapping("/api/v1/places")
    public Long save(@RequestBody PlacesSaveRequestDto requestDto){
        return placesService.save(requestDto);
    }


}
