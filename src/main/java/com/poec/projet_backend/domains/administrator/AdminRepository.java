package com.poec.projet_backend.domains.administrator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUserId(Long id);

    @Query(value = "SELECT ad.firstname, ad.lastname, ad.id as adminId, ad.userId , u.email, u.role, u.createdAt from admin ad  "+
            "JOIN user u on ad.userId = u.id "

            , nativeQuery = true)
    List<Map<String ,Object>> findAllAdminsDetailed();
}
