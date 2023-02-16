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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GoogleClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleClient.class);

    @Value("${google.maps}")
    private String googleApiEndpoint;

    private URI urlCreation() {
        return UriComponentsBuilder.fromHttpUrl(googleApiEndpoint + "/place/nearbysearch/json")
                .queryParam("location", "50.062047%2C19.936279")
                .queryParam("radius", "500")
                .queryParam("type", "currency_exchange")
                .queryParam("keyword", "kantor")
                .queryParam("key", "")
                .build()
                .encode()
                .toUri();
    }

    public List<GoogleNearbyDto> getNearbyCantors() {
//        URI url = urlCreation();
        String url2 = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=50.062047%2C19.936279&radius=1000&type=currency_exchange&keyword=kantor&key=";
        try {
            GoogleReply response2 = restTemplate.getForObject(url2, GoogleReply.class);
            System.out.println(response2);
            return Arrays.stream(Objects.requireNonNull(response2).getResults())
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
//            OkHttpClient client = new OkHttpClient().newBuilder()
//                    .build();
//            Request request = new Request.Builder()
//                    // klucz do dodania
//                    .url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=50.062047%2C19.936279&radius=1000&type=currency_exchange&keyword=kantor&key="+ "")
//                    .method("GET",null)
//                    .build();
//            Response response = client.newCall(request).execute();
//            System.out.println(response);
//            String responseBody = response.body().string();
//            GoogleReply googleReply = objectMapper.readValue(responseBody, GoogleReply.class);
//           return Arrays.stream(googleReply.getResults())
//                    .map(result -> {
//                        GoogleNearbyDto nearbyDto = new GoogleNearbyDto();
//                        nearbyDto.setName(result.getName());
//                        nearbyDto.setRating(result.getRating());
//                        nearbyDto.setVicinity(result.getVicinity());
//                        nearbyDto.setLat(result.getGeometry().getLocation().getLat());
//                        nearbyDto.setLng(result.getGeometry().getLocation().getLng());
//                        return nearbyDto;
//                    })
//                    .collect(Collectors.toList());
//        } catch (RestClientException | IOException e) {
//            LOGGER.error(e.getMessage(), e);
//            return Collections.emptyList();
//        }
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
