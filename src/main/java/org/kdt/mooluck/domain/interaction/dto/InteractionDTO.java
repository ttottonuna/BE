package org.kdt.mooluck.domain.interaction.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class InteractionDTO {
    private int interaction_id;
    private int elder_id;
    private int water_Count;
    private int pet_Count;
    private Timestamp date;
    private String status;

}
