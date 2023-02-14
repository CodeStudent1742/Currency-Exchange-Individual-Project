package com.albert.currency.domain.dto.nbp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NBPResponse {

    @JsonProperty("table")
    private String table;

    @JsonProperty("rates")
    private List<Rate> rates = new ArrayList<>();

    @Override
    public String toString() {
        return "NBPResponse{" +
                "table='" + table + '\'' +
                ", rates=" + rates +
                '}';
    }
}
