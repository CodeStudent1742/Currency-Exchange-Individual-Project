package com.albert.currency.domain.dto.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

    @JsonProperty("geometry")
    private Geometry geometry;
    @JsonProperty("name")
    private String name;
    @JsonProperty("rating")
    private Double rating;
    @JsonProperty("vicinity")
    private String vicinity;

}
