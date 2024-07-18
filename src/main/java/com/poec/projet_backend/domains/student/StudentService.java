package com.poec.projet_backend.domains.student;


import com.poec.projet_backend.user_app.UserApp;
import com.poec.projet_backend.user_app.UserAppRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Data
public class StudentService {
    private final StudentRepository repository;
    private final UserAppRepository userAppRepository;

    public StudentDTO getStudentByUserId(Long userId) throws Exception{
        return StudentDTO.fromEntity(repository.findByUserId(userId));
    }

    public StudentDTO getStudentById(Long userId) throws Exception{
        return StudentDTO.fromEntity(repository.findById(userId).get());
    }

    public StudentDTO updateStudentByUserId(Long userId, Student student) throws Exception {
        try{
        Student studentToUpdate = repository.findByUserId(userId);
        studentToUpdate.setFirstname(student.getFirstname());
        studentToUpdate.setLastname(student.getLastname());
        studentToUpdate.setDescription(student.getDescription());
        studentToUpdate.setTitle(student.getTitle());
        studentToUpdate.setImgUrl(student.getImgUrl());
        studentToUpdate.setGithubUrl(student.getGithubUrl());
        studentToUpdate.setLinkedinUrl(student.getLinkedinUrl());

        return StudentDTO.fromEntity(repository.save(studentToUpdate));}
        catch(Exception e){
            e.printStackTrace();
            throw  new Exception(e.getMessage());
        }
    }

    public Student addStudentByUserId(StudentDTO student) throws Exception{
        try {
            System.out.println(student.toString());
        UserApp user = userAppRepository.findById(student.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        return repository.save(StudentDTO.fromDTO(student, user));
        }
        catch(Exception e){
            e.printStackTrace();
        throw  new Exception(e.getMessage());
        }
    }

    @Transactional
    public void deleteStudentByUserId(Long userId){
        try{
            repository.deleteByUserId(userId);
        }
        catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}