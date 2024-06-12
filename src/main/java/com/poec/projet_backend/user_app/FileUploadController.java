package com.poec.projet_backend.user_app;

import com.poec.projet_backend.domains.mentor.Mentor;
import com.poec.projet_backend.domains.mentor.MentorDTO;
import com.poec.projet_backend.domains.mentor.MentorRepository;
import jakarta.servlet.ServletContext;
import lombok.Data;
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
import java.nio.file.*;
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
    private final ServletContext servletContext;
    private static final String UPLOAD_DIR = "images/";

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
            UUID uuid = UUID.randomUUID();
            fileName = uuid.toString() + "." + extension;
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

    @GetMapping("/serve/{filename}")
    @ResponseBody
    public ResponseEntity< Resource> serveImage(@PathVariable String filename) {
        try {
            String workingDirectory = System.getProperty("user.dir");
            System.out.println(Paths.get(UPLOAD_DIR));
            Path file = Paths.get(workingDirectory + "/src/main/resources/static/images/").resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                String contentType = Files.probeContentType(file);
                return ResponseEntity.ok()
                        // .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, contentType)
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not read the file!", e);
        }
    }


}
