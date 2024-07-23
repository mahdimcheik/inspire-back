package com.poec.projet_backend.domains.superAdmin;
import com.poec.projet_backend.domains.administrator.Admin;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuperAdminDTO {
    String firstname;
    String lastname;
    String imgUrl;

    static public SuperAdminDTO fromEntity(SuperAdmin admin) {
        return SuperAdminDTO.builder()
                .firstname(admin.getFirstname())
                .lastname(admin.getLastname())
                .imgUrl(admin.getImgUrl())
                .build();
    }
}