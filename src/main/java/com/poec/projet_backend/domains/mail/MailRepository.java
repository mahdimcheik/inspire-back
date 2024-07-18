package com.poec.projet_backend.domains.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {

    @Query(value = "SELECT m.* FROM mail m " +
            "WHERE m.senderId = :senderId " +
            "LIMIT :offset, :limit",
            nativeQuery = true)
    List<Map<String, Object>> findMailsBySenderWithPagination(
            @Param("senderId") Long senderId,
            @Param("offset") int offset,
            @Param("limit") int limit);

    @Query(value = "SELECT m.* FROM mail m " +
            "WHERE m.receiverId = :receiverId " +
            "LIMIT :offset, :limit",
            nativeQuery = true)
    List<Map<String, Object>> findMailsByReceiverWithPagination(
            @Param("receiverId") Long receiverId,
            @Param("offset") int offset,
            @Param("limit") int limit);
}





//@Query(value = "SELECT m.*, st.firstname, st.lastname, st.imgUrl, receiver.role FROM mail m " +
//        "JOIN user sender ON sender.id = m.senderId " +
//        "JOIN user receiver ON receiver.id = m.receiverId " +
//        "JOIN student st ON st.userId = receiver.id " +
//        "WHERE sender.id = :senderId " +
//        "LIMIT :offset, :limit",
//        nativeQuery = true)