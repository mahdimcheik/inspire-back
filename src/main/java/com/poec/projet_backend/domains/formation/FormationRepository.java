package com.poec.projet_backend.domains.formation;

import com.poec.projet_backend.domains.experience.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormationRepository extends JpaRepository<Formation, Long> {
    List<Formation> findAllByUserId(Long id);
}
