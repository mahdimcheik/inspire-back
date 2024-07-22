package com.poec.projet_backend.domains.administrator;

import com.poec.projet_backend.domains.mentor.Mentor;
import com.poec.projet_backend.domains.mentor.MentorDTO;
import com.poec.projet_backend.domains.mentor.MentorRepository;
import com.poec.projet_backend.domains.mentor.MentorService;
import com.poec.projet_backend.domains.reservation.ReservationRepository;
import com.poec.projet_backend.domains.slot.SlotRepository;
import com.poec.projet_backend.domains.student.Student;
import com.poec.projet_backend.domains.student.StudentDTO;
import com.poec.projet_backend.domains.student.StudentRepository;
import com.poec.projet_backend.user_app.UserAppRepository;
import com.poec.projet_backend.user_app.UserAppService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Service
public class AdminService {
    private final UserAppService userAppService;
    private final MentorRepository mentorRepository;
    private final StudentRepository studentRepository;
    private final MentorService mentorService;
    private final UserAppRepository userAppRepository;
    private final SlotRepository slotRepository;
    private final EntityManager entityManager;
    private final ReservationRepository reservationRepository;
    private final AdminRepository adminRepository;

    public AdminDTO getProfile(Long id) throws Exception {
        try {
            var admin = adminRepository.findByUserId(id);
            return AdminDTO.fromEntity(admin);

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

    public List<Map<String, Object>> getAllMentors() {
        return mentorRepository.findAllMentorsDetailed();
    }

    public List<Map<String, Object>> getAllStudents() {
        return studentRepository.findAllStudentsDetailed();
    }

    public List<Map<String, Object>> getAllMentorsPaginatedASC(Long perPage, Long offset) {
        return mentorRepository.findAllMentorsDetailedPaginatedASC(perPage, offset);
    }

    public List<Map<String, Object>> getAllStudentsPaginatedASC(Long perPage, Long offset) {
        return studentRepository.findAllStudentsDetailedPaginatedASC(perPage, offset);
    }

    public List<Map<String, Object>> getAllMentorsPaginatedDESC(Long perPage, Long offset) {
        return mentorRepository.findAllMentorsDetailedPaginatedDESC(perPage, offset);
    }

    public List<Map<String, Object>> getAllStudentsPaginatedDESC(Long perPage, Long offset) {
        return studentRepository.findAllStudentsDetailedPaginatedDESC(perPage, offset);
    }

    @Transactional
    public ResponseUpdate updateMentor(Long userId, String firstName, String lastName, String email) throws Exception {
        Mentor mentor = mentorRepository.findByUserId(userId);
        if (mentor == null) {
            throw new Exception("Not found ");
        }

        var user = mentor.getUser();
        user.setEmail(email);
        mentor.setFirstname(firstName);
        mentor.setLastname(lastName);

        var newMentor = mentorRepository.save(mentor);
        return ResponseUpdate.fromMentor(newMentor);
    }

    public ResponseUpdate updateStudent(Long userId, String firstName, String lastName, String email) throws Exception {

        Student student = studentRepository.findByUserId(userId);
        if (student == null) {
            throw new Exception("Not found ");
        }

        var user = student.getUser();
        user.setEmail(email);
        student.setFirstname(firstName);
        student.setLastname(lastName);

        var newStudent = studentRepository.save(student);
        return ResponseUpdate.fromStudent(newStudent);
    }

    public ResponseUpdate updateUser(Long userId, String firstName, String lastName, String email, String role) throws Exception {
        var user = userAppRepository.findById(userId).orElseThrow(() -> new Exception("Not found"));
        if (user.getRole().equals("MENTOR")) {
            return updateMentor(userId, firstName, lastName, email);
        }
        if (user.getRole().equals("STUDENT")) {
            return updateStudent(userId, firstName, lastName, email);
        } else throw new Exception("Not found");
    }

    @Transactional
    public void deleteFavorite(Long mentorId) throws Exception {
        mentorRepository.deleteFavoriteByMentorId(mentorId);
    }

    @Transactional
    public Map<String, String> changeMentorRole(Long userId, String role) throws Exception {
        var user = userAppRepository.findById(userId).orElseThrow(() -> new Exception("Not found"));
        Mentor mentor = mentorRepository.findByUserId(userId);

        if (mentor == null) {
            throw new Exception("Mentor not found");
        }

        mentorRepository.deleteFavoriteByMentorId(mentor.getId());

        user.setRole(role);
        Student newStudent = Mentor.toStudent(user.getMentor());

        for (Student student : mentor.getStudents()) {
            student.getMentors().remove(mentor);
        }
        mentor.getStudents().clear();

        user.setMentor(null);
        user.setStudent(newStudent);

        userAppRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return response;
    }

    @Transactional
    public Map<String, String> changeStudentRole(Long userId, String role) throws Exception {
        var user = userAppRepository.findById(userId).orElseThrow(() -> new Exception("Not found"));
        Student student = studentRepository.findByUserId(userId);

        if (student == null) {
            throw new Exception("Mentor not found");
        }

        studentRepository.deleteFavoriteByStudentId(student.getId());

        user.setRole(role);
        Mentor newMentor = Student.toMentor(user.getStudent());

        for (Mentor mentor : student.getMentors()) {
            mentor.getStudents().remove(student);
        }
        student.getMentors().clear();

        user.setStudent(null);
        user.setMentor(newMentor);

        userAppRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return response;
    }
//    public Map<String ,String> changeStudentRole(Long userId, String role) throws Exception{
//        var user = userAppRepository.findById(userId).orElseThrow(() -> new Exception("Not found"));
//        Student student = studentRepository.findByUserId(userId);
//        // deleteFavorite(student.getId());
//
//        user.setRole(role);
//        Mentor newMentor = Student.toMentor(user.getStudent());
//        studentRepository.delete(student);
//        user.setMentor(null);
//        user.setMentor(newMentor);
//
//        userAppRepository.save(user);
//
//        Map<String ,String> response = new HashMap<>();
//        response.put("status", "success");
//        return response;
//    }

    public Map<String, String> updateRole(Long userId, String role) throws Exception {
        var user = userAppRepository.findById(userId).orElseThrow(() -> new Exception("Not found"));
        if (user.getRole().equals("MENTOR")) {
            return changeMentorRole(userId, role);
        } else if (user.getRole().equals("STUDENT")) {
            return changeStudentRole(userId, role);
        }
        Map<String, String> response = new HashMap<>();
        response.put("status", "nothing");
        return response;
    }

    public void deleteReservationsByMentorId(Long mentorId) throws Exception {
        //reservationRepository.deleteReservationsByMentorId(mentorId);
        slotRepository.deleteByMentorId(mentorId);
    }
}
