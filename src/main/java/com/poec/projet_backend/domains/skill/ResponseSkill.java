package com.poec.projet_backend.domains.skill;

import lombok.Builder;
import lombok.Data;
import org.aspectj.bridge.IMessage;

import java.util.List;

@Data
@Builder
public class ResponseSkill {
    private String message;
    private boolean success;
    private List<SkillDTO> skills;
}
