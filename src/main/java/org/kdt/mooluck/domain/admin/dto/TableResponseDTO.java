package org.kdt.mooluck.domain.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TableResponseDTO {
    private boolean success;
    private ResponseDataDTO response;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ResponseDataDTO {
        private List<AgencyTableDTO> data;
    }
}
