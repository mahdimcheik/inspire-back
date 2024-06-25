package com.poec.projet_backend.domains.experience;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findAllByUserId(Long id);

    @Query("SELECT e FROM Experience e WHERE e.user.id = :userId")
    List<Experience> findByUserId(Long userId);
}
