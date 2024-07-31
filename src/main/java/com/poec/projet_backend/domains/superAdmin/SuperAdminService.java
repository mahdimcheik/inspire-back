package com.poec.projet_backend.domains.superAdmin;

import com.poec.projet_backend.domains.administrator.Admin;
import com.poec.projet_backend.domains.administrator.AdminDTO;
import com.poec.projet_backend.domains.administrator.AdminRepository;
import com.poec.projet_backend.user_app.UserAppRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Data
@Service
public class SuperAdminService {
    private final SuperAdminRepository superAdminRepository;
    private final UserAppRepository userAppRepository;
    private final AdminRepository adminRepository;

    public SuperAdminDTO getProfile(Long id) throws Exception {
        try {
            var superAdmin = superAdminRepository.findByUserId(id);
            return SuperAdminDTO.fromEntity(superAdmin);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public SuperAdminDTO createSuperAdmin(Long userId, SuperAdminDTO adminDTO) throws Exception {
        try {
            var user = userAppRepository.findById(userId).orElseThrow(() ->  new Exception("User not found"));
            SuperAdmin admin = SuperAdmin.builder()
                    .imgUrl(adminDTO.getImgUrl())
                    .firstname(adminDTO.getFirstname())
                    .lastname(adminDTO.getLastname())
                    .user(user)
                    .build();
            var newAdmin = superAdminRepository.save(admin);
            return SuperAdminDTO.fromEntity(newAdmin);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public AdminDTO createAdmin(Long userId, AdminDTO adminDTO) throws Exception {
        try {
            var user = userAppRepository.findById(userId).orElseThrow(() ->  new Exception("User not found"));
            Admin admin = Admin.builder()
                    .imgUrl(adminDTO.getImgUrl())
                    .firstname(adminDTO.getFirstname())
                    .lastname(adminDTO.getLastname())
                    .user(user)
                    .build();
            var newAdmin = adminRepository.save(admin);
            return AdminDTO.fromEntity(newAdmin);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public List<Map<String, Object>> getAllAdmins() throws Exception {
        try{
           return  adminRepository.findAllAdminsDetailed();

        }catch (Exception e){
            e.printStackTrace();
            throw  new Exception("Not Found");
        }
    }
}
