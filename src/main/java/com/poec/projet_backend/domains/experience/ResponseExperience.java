package com.poec.projet_backend.domains.experience;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class ResponseExperience {
    List< ExperienceDTO> experiences;
    String message;
    boolean success;
}
