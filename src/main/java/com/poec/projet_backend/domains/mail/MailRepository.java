package com.poec.projet_backend.domains.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {
    List<Mail> findBySenderId(Long senderId);
    List<Mail> findByReceiverId(Long receiverId);

}
