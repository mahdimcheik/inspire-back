package com.poec.projet_backend.domains.formation;

import com.poec.projet_backend.domains.experience.ExperienceDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseFormation {
    List<FormationDTO> formations;
    String message;
    boolean success;
}
