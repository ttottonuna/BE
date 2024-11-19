package org.kdt.mooluck.domain.admin.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

public class AgencyTableDTO {
    private Long interactionId;   // Interaction 테이블의 interaction_id
    private Long totalCount;      // Interaction 테이블의 water_count + pet_count
    private String status;        // Interaction 테이블의 status
    private Long elderId;         // Elder 테이블의 elder_id
    private String elderName;     // Elder 테이블의 name
    private String elderAddress;  // Elder 테이블의 address
    private LocalDateTime lastCheckIn; // Interaction 테이블의 last_interaction
}
