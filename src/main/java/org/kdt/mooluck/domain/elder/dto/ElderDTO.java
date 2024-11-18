package org.kdt.mooluck.domain.elder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElderDTO {
    private Long elderId;
    private String elderAccount;
    private String elderPwd;

}
