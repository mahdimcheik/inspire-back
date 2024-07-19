package com.poec.projet_backend.domains.administrator;

import com.poec.projet_backend.domains.mentor.MentorService;
import com.poec.projet_backend.domains.student.StudentDTO;
import com.poec.projet_backend.domains.student.StudentService;
import jakarta.websocket.server.PathParam;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@RestController
@RequestMapping("admin")
public class AdminController {
    private final AdminService adminService;
    private final MentorService mentorService;
    private final StudentService studentService;

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

    @DeleteMapping("/delete/mentor/{userId}")
    public Map<String, String> deleteMentor(@PathVariable("userId") Long userId) {
        try {
            mentorService.deleteMentorByUserId(userId);
            Map<String, String> result =  new HashMap<>();
            result.put("status", "success");
            return  result;
        }catch(Exception e) {
            e.printStackTrace();
            Map<String, String> result =  new HashMap<>();
            result.put("status", "Failed");
            return  result;
        }
    }

    @DeleteMapping("/delete/student/{userId}")
    public Map<String, String> deleteStudent(@PathVariable("userId") Long userId) {
        try {
            studentService.deleteStudentByUserId(userId);
            Map<String, String> result =  new HashMap<>();
            result.put("status", "success");
            return  result;
        }catch(Exception e) {
            e.printStackTrace();
            Map<String, String> result =  new HashMap<>();
            result.put("status", "Failed");
            return  result;
        }
    }

    @PutMapping("update/{userId}")
    public ResponseUpdate updateUser(@PathVariable("userId") Long userId, @RequestBody ResponseUpdate request) throws Exception {
        return adminService.updateUser(userId,request.getFirstname(),request.getLastname(), request.getEmail(), request.getRole());
    }
    @PutMapping("update/role/{userId}")
    public Map<String ,String> updateUser(@PathVariable("userId") Long userId, @PathParam("role") String role) throws Exception {
        System.out.println("change in controller");
        return adminService.updateRole(userId,role);
    }
}
