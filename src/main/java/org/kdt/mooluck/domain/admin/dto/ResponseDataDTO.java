package org.kdt.mooluck.domain.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDataDTO {
    private List<AgencyTableDTO> data;  // JSON의 "response.data" 필드
}
