package com.albert.currency.domain.dto;

import lombok.Data;

@Data
public class GoogleNearbyDto {

    private String name;
    private Double rating;
    private Double lat;
    private Double lng;
    private String vicinity;

}
