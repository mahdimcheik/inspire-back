package com.poec.projet_backend.domains.mail;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("mails")
@Data
public class MailController {
    private final MailService mailService;

    @PostMapping("/send/{senderId}")
    public ResponseEntity<?> sendMail(@PathVariable("senderId") Long senderId, @RequestBody MailSend mail) {
        try{
            var sentMail = mailService.sendMail(senderId,mail.getReceiverId(), LocalDateTime.now(),mail.getTitle(),mail.getBody());
            return new ResponseEntity<>(MailDTO.fromEntity( sentMail),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sender/{id}")
    public ResponseEntity< List<MailDTO>> getSentMails(@PathVariable Long id) {
        try{
            return new ResponseEntity<>( mailService.findSentMailForUser(id).stream().map(MailDTO::fromEntity).toList(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/receiver/{id}")
    public ResponseEntity< List<MailDTO>> getReceiverMails(@PathVariable Long id) {
        try{
            return new ResponseEntity<>( mailService.findReceivedMailForUser(id).stream().map(MailDTO::fromEntity).toList(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MailDTO> updateMails(@PathVariable Long id, @RequestBody MailDTO mail) {
        try{
            return new ResponseEntity<>(MailDTO.fromEntity( mailService.updateMail(id, mail)), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
