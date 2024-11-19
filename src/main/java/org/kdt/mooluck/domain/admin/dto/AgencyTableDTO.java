package org.kdt.mooluck.domain.admin.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AgencyTableDTO {
    private Long interactionId;        // i.interaction_id
    private Long totalCount;           // CAST(i.water_count + i.pet_count AS SIGNED)
    private String status;             // i.status
    private Long elderId;              // e.elder_id
    private String elderName;          // e.elder_name
    private String elderAddress;       // e.elder_address
    private LocalDateTime lastCheckIn; // i.last_interaction
    private Long staffId;              // e.staff_id
}