package org.kdt.mooluck.domain.petmanagement.dto;

import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
public class PetManagementDTO {
    private int pet_id;
    private int interaction_id;
    private int pet_count;
    private Timestamp created_at;
    private String pet_keyword;
}
