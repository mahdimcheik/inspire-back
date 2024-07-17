package com.poec.projet_backend.domains.mail;

import com.poec.projet_backend.domains.mentor.Mentor;
import com.poec.projet_backend.domains.mentor.MentorRepository;
import com.poec.projet_backend.domains.student.Student;
import com.poec.projet_backend.domains.student.StudentRepository;
import com.poec.projet_backend.user_app.UserAppRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Data
public class MailService {
    private final MailRepository mailRepository;
    private final UserAppRepository userRepository;
    private final MentorRepository mentorRepository;
    private final StudentRepository studentRepository;

    public Mail sendMail(Long from, Long to, LocalDateTime sentAt, String subject, String body) {
        try{
            var sender = userRepository.findById(from).orElseThrow(() -> new RuntimeException("Not found exception"));
            var receiver = userRepository.findById(to).orElseThrow(() -> new RuntimeException("Not found exception"));
            String senderFirstname = "";
            String senderLastname = "";
            String imgUrl = "";

            boolean isMentor = sender.getRole().equals("MENTOR");
            boolean isStudent = sender.getRole().equals("STUDENT");

            if(isMentor){
                Mentor mentor = mentorRepository.findByUserId(sender.getId());
                senderFirstname = mentor.getFirstname();
                senderLastname = mentor.getLastname();
                imgUrl = mentor.getImgUrl();
            }
            if(isStudent){
                Student student = studentRepository.findByUserId(sender.getId());
                senderFirstname = student.getFirstname();
                senderLastname = student.getLastname();
                imgUrl = student.getImgUrl();
            }
            Mail newMail  = Mail.builder()
                    .title(subject)
                    .body(body)
                    .sentDate(LocalDateTime.now())
                    .sender(sender).receiver(receiver)
                    .senderFirstName(senderFirstname)
                    .senderLastName(senderLastname)
                    .senderRole(sender.getRole())
                    .imgUrl(imgUrl)
                    .isOpened(false)
                    .build();

            return mailRepository.save(newMail);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Mail> findReceivedMailForUser(Long id) {
        try{
           List<Mail> mails  = mailRepository.findByReceiverId(id);

            return mails;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Mail> findSentMailForUser(Long id) {
        try{
            List<Mail> mails  = mailRepository.findBySenderId(id);

            return mails;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public Mail updateMail(Long id,MailDTO mailDTO) {
        try{
            Mail mail = mailRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found exception"));
            mail.setOpened(mailDTO.isOpened());
            return mailRepository.save(mail);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
