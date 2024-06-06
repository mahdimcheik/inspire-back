package com.poec.projet_backend.domains.skill;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class SkillService {
    private final SkillRepository repository;

public List<SkillDTO> getAllSkills() {
    return repository.findAll().stream().map(SkillDTO::fromSkill).toList();
}}
