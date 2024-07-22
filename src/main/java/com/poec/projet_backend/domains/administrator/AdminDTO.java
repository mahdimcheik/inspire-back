package com.poec.projet_backend.domains.administrator;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminDTO {
    String firstname;
    String lastname;
    String imgUrl;

    static public AdminDTO fromEntity(Admin admin) {
        return AdminDTO.builder()
                .firstname(admin.getFirstname())
                .lastname(admin.getLastname())
                .build();
    }
}
