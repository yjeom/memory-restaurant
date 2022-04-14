package com.yjeom.pro01.memoryrestaurant.controller;

import com.yjeom.pro01.memoryrestaurant.domain.Place;
import com.yjeom.pro01.memoryrestaurant.repository.PlaceRepository;
import com.yjeom.pro01.memoryrestaurant.dto.PlacesSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlaceMemoApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PlaceRepository placeRepository;

    @After
    public void tearDown() throws Exception{
        placeRepository.deleteAll();
    }

    @Test
    public void Places_등록하다() throws Exception{
        String placeName="명동 교자";
        String content="맛있어요!";
        double positionX=126.98561429978552;
        double positionY=37.56255453417897;
        PlacesSaveRequestDto requestDto = PlacesSaveRequestDto.builder()
                .placeName(placeName)
                .content(content)
                .positionX(positionX)
                .positionY(positionY)
                .build();
        String url="http://localhost:"+port+"/api/v1/places";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto ,Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Place> all= placeRepository.findAll();
        assertThat(all.get(0).getPlaceName()).isEqualTo(placeName);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }
}
