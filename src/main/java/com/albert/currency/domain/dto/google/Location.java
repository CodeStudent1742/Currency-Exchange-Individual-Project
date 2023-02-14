
package com.albert.currency.domain.dto.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

    @JsonProperty("lat")
    private Double lat;
    @JsonProperty("lng")
    private Double lng;

}
