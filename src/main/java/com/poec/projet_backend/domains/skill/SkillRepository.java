package com.poec.projet_backend.domains.skill;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByName(String name);
    List<Skill> findByNameIn(List<String> names);
}
