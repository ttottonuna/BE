package org.kdt.mooluck.domain.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherDTO {
    private String weatherIcon;
    private Integer timestamp;

}
