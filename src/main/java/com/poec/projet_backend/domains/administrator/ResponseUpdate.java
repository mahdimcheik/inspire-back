package com.poec.projet_backend.domains.administrator;

import com.poec.projet_backend.domains.mentor.Mentor;
import com.poec.projet_backend.domains.student.Student;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseUpdate {
    private String firstname;
    private String lastname;
    private String email;
    private String role;

    public static ResponseUpdate fromMentor(Mentor mentor){
        return ResponseUpdate.builder()
                .firstname(mentor.getFirstname())
                .lastname(mentor.getLastname())
                .email(mentor.getUser().getEmail())
                .role(mentor.getUser().getRole())
                .build();
    }

    public static ResponseUpdate fromStudent(Student student){
        return ResponseUpdate.builder()
                .firstname(student.getFirstname())
                .lastname(student.getLastname())
                .email(student.getUser().getEmail())
                .role(student.getUser().getRole())
                .build();
    }
}
