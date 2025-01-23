package org.kdt.mooluck.domain.admin.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AgencyTableDTO {
    private Long interactionId;
    private Long totalCount;
    private String status;
    private Long elderId;
    private String elderName;
    private String elderAddress;
    private LocalDateTime lastCheckIn;
    private Long staffId;
    private String elderNumber;
    private int firstInterval;
    private int secondInterval;
    private int thirdInterval;
    private int fourthInterval;
}