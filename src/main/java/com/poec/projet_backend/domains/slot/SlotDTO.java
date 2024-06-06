package com.poec.projet_backend.domains.slot;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SlotDTO {
    private Long id;
    private String dateBegin;
    private String dateEnd;
    private boolean visio;
    private boolean isBooked;
    private Long userId;
}
