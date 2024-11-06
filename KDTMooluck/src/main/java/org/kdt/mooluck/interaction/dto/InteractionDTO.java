package org.kdt.mooluck.interaction.dto;

import lombok.*;
import java.time.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InteractionDTO {
    private int interaction_id;
    private int elder_id;
    private int water_count;
    private int pet_count;
    private LocalDateTime last_interaction;
    private String status;

}
