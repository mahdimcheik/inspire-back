package com.poec.projet_backend.domains.mail;

import com.poec.projet_backend.domains.mentor.MentorRepository;
import com.poec.projet_backend.domains.mentor.MentorService;
import com.poec.projet_backend.domains.reservation.RangeDate;
import com.poec.projet_backend.domains.student.StudentRepository;
import com.poec.projet_backend.user_app.UserAppRepository;
import com.poec.projet_backend.user_app.UserAppService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Data
public class MailService {
    private final MailRepository mailRepository;
    private final UserAppRepository userAppRepository;
    private final MentorRepository mentorRepository;
    private final StudentRepository studentRepository;


    public Mail sendMail(MailSendDTO mail) {
        var sender = userAppRepository.findById(mail.getSenderId()).orElseThrow(() -> new RuntimeException("User not found"));
        var receiver = userAppRepository.findById(mail.getReceiverId()).orElseThrow(() -> new RuntimeException("User not found"));


         String senderFirstname = "";
         String senderLastname = "";
         String senderRole = sender.getRole();

         String receiverFirstname = "";
         String receiverLastname = "";
         String receiverRole = receiver.getRole();

         if(sender.getRole().equals("MENTOR")){
             var mentor = mentorRepository.findByUserId(sender.getId());
             senderFirstname = mentor.getFirstname();
             senderLastname = mentor.getLastname();
         }
        if(sender.getRole().equals("STUDENT")){
            var student = studentRepository.findByUserId(sender.getId());
            senderFirstname = student.getFirstname();
            senderLastname = student.getLastname();
        }

        if(receiver.getRole().equals("MENTOR")){
            var mentor = mentorRepository.findByUserId(receiver.getId());
            receiverFirstname = mentor.getFirstname();
            receiverLastname = mentor.getLastname();
        }
        if(receiver.getRole().equals("STUDENT")){
            var student = studentRepository.findByUserId(receiver.getId());
            receiverFirstname = student.getFirstname();
            receiverLastname = student.getLastname();
        }

        try{
            if(sender != null && receiver != null && mail.getTitle() != null && mail.getBody() != null) {
                var newMail = Mail.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .title(mail.getTitle())
                        .body(mail.getBody())
                        .isOpened(false)
                        .sentDate(LocalDateTime.now())
                        .receiverFirstname(receiverFirstname)
                        .receiverLastname(receiverLastname)
                        .receiverRole(receiverRole)
                        .senderLastname(senderLastname)
                        .senderFirstname(senderFirstname)
                        .senderRole(senderRole)
                        .build();
                return mailRepository.save(newMail);
            }
            return null;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Map<String, Object>> getSentMails(Long senderId, int limit, int offset){
        return mailRepository.findMailsBySenderWithPagination(senderId, offset, limit);
    }

    public List<Map<String, Object>> getReceivedMails(Long receiverId, int limit, int offset){
        return mailRepository.findMailsByReceiverWithPagination(receiverId, offset, limit);
    }
}
