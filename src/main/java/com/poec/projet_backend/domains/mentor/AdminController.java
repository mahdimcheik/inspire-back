package com.poec.projet_backend.domains.mentor;

import com.poec.projet_backend.domains.administrator.AdminService;
import jakarta.websocket.server.PathParam;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Data
@RestController
@RequestMapping("admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/get/mentors")
    public List<Map<String, Object>> getMentors() {
        return adminService.getAllMentors();
    }
    @GetMapping("/get/students")
    public List<Map<String, Object>> getStudents() {
        return adminService.getAllStudents();
    }
    @GetMapping("/get/mentors/paginated/asc")
    public List<Map<String, Object>> getAllMentorsPaginatedASC(@PathParam("limit") Long limit, @PathParam("offset") Long offset) {
        return adminService.getAllMentorsPaginatedASC(limit, offset);
    }

    @GetMapping("/get/students/paginated/asc")
    public List<Map<String, Object>> getAllStudentsPaginatedASC(@PathParam("limit") Long limit, @PathParam("offset") Long offset) {
        return adminService.getAllStudentsPaginatedASC(limit, offset);
    }

    @GetMapping("/get/mentors/paginated/desc")
    public List<Map<String, Object>> getAllMentorsPaginatedDESC(@PathParam("limit") Long limit, @PathParam("offset") Long offset) {
        return adminService.getAllMentorsPaginatedDESC(limit, offset);
    }

    @GetMapping("/get/students/paginated/desc")
    public List<Map<String, Object>> getAllStudentsPaginatedDESC(@PathParam("limit") Long limit, @PathParam("offset") Long offset) {
        return adminService.getAllStudentsPaginatedDESC(limit, offset);
    }
}
