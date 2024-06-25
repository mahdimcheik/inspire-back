package com.poec.projet_backend.domains.mentor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long>{
    Mentor findByUserId(Long userId);
}
