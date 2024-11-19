package org.kdt.mooluck.domain.weather.controller;

import lombok.extern.slf4j.Slf4j;
import org.kdt.mooluck.custom.CustomResponse;
import org.kdt.mooluck.domain.weather.dto.WeatherDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("/api/weather")
@Slf4j
@PropertySource("classpath:/application.properties")
public class WeatherController {

    @Value("${weather.url}")
    private String URL;

    @Value("${weather.api_key}")
    private String API_KEY;

    @GetMapping
    public ResponseEntity<CustomResponse> getWeather(@RequestParam double lat, @RequestParam double lon) {
        try {
            // RestTemplate 만들고 접속할 openapi 경로
            RestTemplate restTemplate = new RestTemplate();
            String url = UriComponentsBuilder.fromHttpUrl(URL)
                    .queryParam("lat", lat)
                    .queryParam("lon", lon)
                    .queryParam("APPID", API_KEY)
                    .toUriString();

            // openapi 리턴값을 map 형태의 response로 저장
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            // 그 중에서 icon, timestamp만 추출
            String icon = (String) ((Map<String, Object>) ((Map<String, Object>) ((java.util.List<?>) response.get("weather")).get(0))).get("icon");
            Integer timestamp = (Integer) response.get("dt");

            // weatherDTO에 넣고 리턴
            WeatherDTO weatherDTO = new WeatherDTO();
            weatherDTO.setWeatherIcon(icon);
            weatherDTO.setTimestamp(timestamp);

            return ResponseEntity.ok(CustomResponse.success(weatherDTO));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CustomResponse.message("날씨 정보를 가져오는 중 오류가 발생했습니다."));
        }
    }
}
