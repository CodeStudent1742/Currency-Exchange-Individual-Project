package com.albert.currency.controller;

import com.albert.currency.client.GoogleClient;
import com.albert.currency.domain.dto.GoogleNearbyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/google")
public class GoogleController {

    private final GoogleClient googleClient;

    //For "manual" implementation testing only
    @GetMapping("/cantor/nearby")
    public void getNearbyCantors() {
        List<GoogleNearbyDto> nearbyCantor = googleClient.getNearbyCantors();
        for(GoogleNearbyDto cantor: nearbyCantor){
            System.out.println(cantor);
        }
    }
}
