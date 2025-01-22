package org.kdt.mooluck.domain.admin.dto;

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

    private String elderName;
    private String elderAddress;
    private String elderNumber;

    private String elderAccount;
    private String elderPwd;

    private Integer staffId;
}
