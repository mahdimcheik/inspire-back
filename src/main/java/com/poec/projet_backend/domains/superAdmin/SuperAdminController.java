package com.poec.projet_backend.domains.superAdmin;

import com.poec.projet_backend.domains.administrator.AdminDTO;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Data
@RestController
@RequestMapping("superadmin")
public class SuperAdminController {

    private final SuperAdminService superAdminService;

    @GetMapping("/get/profile/{id}")
    public SuperAdminDTO getProfile(@PathVariable("id") Long id) throws Exception {
        return superAdminService.getProfile(id);
    }

    @GetMapping("/get/admins")
    public List<Map<String, Object>> getAdmins() throws Exception {
     return superAdminService.getAllAdmins();
    }
}
