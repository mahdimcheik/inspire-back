package com.poec.projet_backend.domains.mentor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long>{
    Mentor findByUserId(Long userId);

    @Query(value = "SELECT mt.firstname, mt.lastname, mt.id as mentorId, mt.userId , u.email, u.role, u.createdAt from mentor mt  "+
            "JOIN user u on mt.userId = u.id "

            , nativeQuery = true)
    List<Map<String ,Object>> findAllMentorsDetailed();


    @Query(value = "SELECT mt.firstname, mt.lastname, mt.id as mentorId, mt.userId, u.email, u.role, u.createdAt " +
            "FROM mentor mt " +
            "JOIN user u ON mt.userId = u.id " +
            "ORDER BY u.createdAt ASC " +
            "LIMIT :perPage OFFSET :offset",
            nativeQuery = true)
    List<Map<String, Object>> findAllMentorsDetailedPaginatedASC(@Param("perPage") Long perPage, @Param("offset") Long offset);

    @Query(value = "SELECT mt.firstname, mt.lastname, mt.id as mentorId, mt.userId, u.email, u.role, u.createdAt " +
            "FROM mentor mt " +
            "JOIN user u ON mt.userId = u.id " +
            "ORDER BY u.createdAt DESC " +
            "LIMIT :perPage OFFSET :offset",
            nativeQuery = true)
    List<Map<String, Object>> findAllMentorsDetailedPaginatedDESC(@Param("perPage") Long perPage, @Param("offset") Long offset);

    void deleteByUserId(Long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM favorite_mentor_student WHERE mentors_id = :mentorId", nativeQuery = true)
    void deleteFavoriteByMentorId(@Param("mentorId") Long mentorId);

}
