package com.albert.currency.client;

import com.albert.currency.domain.dto.GoogleNearbyDto;
import com.albert.currency.domain.dto.google.GoogleReply;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GoogleClient {
    private final ObjectMapper objectMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleClient.class);

    @Value("${google.maps}")
    private String googleApiEndpoint;
    @Value(("${google.key}"))
    private String googleApiKey;
    private static String CANTORS_NEARBY_KRK_MAIN_SQUARE_URL ="/place/nearbysearch/json?location=50.062047%2C19.936279&radius=200&type=currency_exchange&keyword=kantor&key=";
    public List<GoogleNearbyDto> getNearbyCantors() {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(googleApiEndpoint + CANTORS_NEARBY_KRK_MAIN_SQUARE_URL + googleApiKey)
                    .method("GET",null)
                    .build();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            GoogleReply googleReply = objectMapper.readValue(responseBody, GoogleReply.class);
            return Arrays.stream(googleReply.getResults())
                    .map(result -> {
                        GoogleNearbyDto nearbyDto = new GoogleNearbyDto();
                        nearbyDto.setName(result.getName());
                        nearbyDto.setRating(result.getRating());
                        nearbyDto.setVicinity(result.getVicinity());
                        nearbyDto.setLat(result.getGeometry().getLocation().getLat());
                        nearbyDto.setLng(result.getGeometry().getLocation().getLng());
                        return nearbyDto;
                    })
                    .collect(Collectors.toList());
        } catch (RestClientException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}


