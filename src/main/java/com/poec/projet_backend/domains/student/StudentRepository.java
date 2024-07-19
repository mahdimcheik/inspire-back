package com.poec.projet_backend.domains.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUserId(Long userId);

    @Query(value = "SELECT st.firstname, st.lastname, st.id as studentId, st.userId , u.email, u.role, u.createdAt from student st  "+
            "JOIN user u on st.userId = u.id "

            , nativeQuery = true)
    List<Map<String ,Object>> findAllStudentsDetailed();


    @Query(value = "SELECT st.firstname, st.lastname, st.id as studentId, st.userId, u.email, u.role, u.createdAt " +
            "FROM student st " +
            "JOIN user u ON st.userId = u.id " +
            "ORDER BY u.createdAt ASC " +
            "LIMIT :perPage OFFSET :offset",
            nativeQuery = true)
    List<Map<String, Object>> findAllStudentsDetailedPaginatedASC(@Param("perPage") Long perPage, @Param("offset") Long offset);

    @Query(value = "SELECT st.firstname, st.lastname, st.id as studentId, st.userId, u.email, u.role, u.createdAt " +
            "FROM student st " +
            "JOIN user u ON st.userId = u.id " +
            "ORDER BY u.createdAt DESC " +
            "LIMIT :perPage OFFSET :offset",
            nativeQuery = true)
    List<Map<String, Object>> findAllStudentsDetailedPaginatedDESC(@Param("perPage") Long perPage, @Param("offset") Long offset);

    void deleteByUserId(Long userId);
}
