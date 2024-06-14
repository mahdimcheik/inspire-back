package com.poec.projet_backend.user_app;

import com.poec.projet_backend.domains.mentor.MentorService;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class FileUploadService {
    private MentorService mentorService;


}
