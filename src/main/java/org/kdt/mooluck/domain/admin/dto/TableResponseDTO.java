package org.kdt.mooluck.domain.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TableResponseDTO {
    private boolean success;                    // JSON의 "success" 필드
    private ResponseDataDTO response;          // JSON의 "response" 필드

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ResponseDataDTO {
        private List<AgencyTableDTO> data;     // JSON의 "response.data" 필드
    }
}
