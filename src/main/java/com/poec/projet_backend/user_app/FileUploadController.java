package com.poec.projet_backend.user_app;

import com.poec.projet_backend.domains.mentor.Mentor;
import com.poec.projet_backend.domains.mentor.MentorDTO;
import com.poec.projet_backend.domains.mentor.MentorRepository;
import com.poec.projet_backend.domains.mentor.MentorService;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.time.LocalDateTime;

@RestController
@Data
@RequestMapping("/user/upload")
public class FileUploadController {


    private final MentorRepository mentorRepository;
    @PostMapping("/image/{userId}")
    @Transactional
    public ResponseEntity<MentorDTO> handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable Long userId) {
        System.out.println("called");
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        System.out.println("file not empty " + file.getOriginalFilename());
        try {
            String fileName = file.getOriginalFilename();
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            fileName = (fileName + LocalDateTime.now().toString()).replaceAll("[^a-zA-Z0-9].", "") + "." + extension;
            System.out.println("new name " + fileName);
            String workingDirectory = System.getProperty("user.dir");
            System.out.println("file details " + file.getResource());
            Mentor updatedMentor = mentorRepository.findByUserId(userId);
            updatedMentor.setImgUrl("http://localhost:8080/images/" + fileName);
            MentorDTO res = MentorDTO.fromEntity(mentorRepository.save(updatedMentor));
            file.transferTo(new File(workingDirectory + "/src/main/resources/static/images/" + fileName));

            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
