package com.albert.currency.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rate {

    @JsonProperty("code")
    private String code;
    @JsonProperty("bid")
    private Double bid;
    @JsonProperty("ask")
    private Double ask;

    @Override
    public String toString() {
        return "Rate{" +
                "code='" + code + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                '}';
    }
}
