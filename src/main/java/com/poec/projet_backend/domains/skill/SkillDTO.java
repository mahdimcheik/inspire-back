package com.poec.projet_backend.domains.skill;


public record SkillDTO(
        String name,
        Long id
) {
    public static SkillDTO fromSkill(Skill skill) {
        return new SkillDTO(skill.getName(), skill.getId());
    }
}
