package com.poec.projet_backend.domains.slot;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findAllByUserId(Long id);
}
