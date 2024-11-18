package org.kdt.mooluck.domain.interaction.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InteractionDTO {
    private int interaction_Id;
    private int elder_Id;
    private int water_Count;
    private int pet_Count;
    private String last_Interaction;
    private String status;
}
