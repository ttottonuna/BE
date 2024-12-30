package org.kdt.mooluck.domain.watermanagement.dto;

import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
public class WaterManagementDTO {
    private int water_id;
    private int interaction_id;
    private int water_count;
    private Timestamp created_at;
    private String water_keyword;
}
