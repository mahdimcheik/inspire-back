package com.poec.projet_backend.user_app;

import com.poec.projet_backend.domains.mentor.Mentor;
import com.poec.projet_backend.domains.mentor.MentorDTO;
import com.poec.projet_backend.domains.mentor.MentorRepository;
import com.poec.projet_backend.domains.student.Student;
import com.poec.projet_backend.domains.student.StudentDTO;
import com.poec.projet_backend.domains.student.StudentRepository;
import jakarta.servlet.ServletContext;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@Data
@RequestMapping("/user/upload")
public class FileUploadController {

    private final MentorRepository mentorRepository;
    private final StudentRepository studentRepository;
    private final ServletContext servletContext;
    private static final String UPLOAD_DIR = "uploads/images/";

    @PostMapping("/image/mentor/{userId}")
    public ResponseEntity<MentorDTO> uploadImage(@RequestParam("file") MultipartFile file, @PathVariable Long userId) {
        try {
            // Generate a unique file name
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);

            // Save the file locally
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Mentor updatedMentor = mentorRepository.findByUserId(userId);
            String filename = updatedMentor.getImgUrl();
            filename = getLastPartOfUrl(filename);
            Path oldFilePath = Paths.get(UPLOAD_DIR + filename);
            if (Files.exists(oldFilePath)) {
                Files.delete(oldFilePath);
            }
            updatedMentor.setImgUrl("http://localhost:8080/user/upload/" + fileName);
            MentorDTO res = MentorDTO.fromEntity(mentorRepository.save(updatedMentor));

            return ResponseEntity.ok(res);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/image/student/{userId}")
    public ResponseEntity<StudentDTO> uploadImageStudent(@RequestParam("file") MultipartFile file, @PathVariable Long userId) {
        try {
            // Generate a unique file name
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);

            // Save the file locally
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Student updatedMentor = studentRepository.findByUserId(userId);
            updatedMentor.setImgUrl("http://localhost:8080/user/upload/" + fileName);
            StudentDTO res = StudentDTO.mapFromEntity(studentRepository.save(updatedMentor));

            return ResponseEntity.ok(res);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR + filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(filePath));
                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (MalformedURLException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static String getLastPartOfUrl(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }
        int lastSlashIndex = url.lastIndexOf('/');
        if (lastSlashIndex == -1) {
            return url; // Return the whole URL if no slash is found
        }
        return url.substring(lastSlashIndex + 1);
    }
}
