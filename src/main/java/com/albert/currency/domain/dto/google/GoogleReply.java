package com.albert.currency.domain.dto.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleReply {

    @JsonProperty("results")
    private Result[] results;
    @JsonProperty("next_page_token")
    public String nextPageToken;

}
