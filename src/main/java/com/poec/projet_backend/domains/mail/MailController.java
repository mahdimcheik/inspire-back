package com.poec.projet_backend.domains.mail;

import jakarta.websocket.server.PathParam;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Data
@RestController
@RequestMapping("/mails")
public class MailController {
    private final MailService mailService;

    @PostMapping("/add")
    public String add(@RequestBody MailSendDTO mail) {
        try{
           mailService.sendMail(mail);
           return "OK";
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @GetMapping("/get/sent/{senderId}")
    public List<Map<String,Object>> getSentMails(@PathVariable("senderId") Long senderId, @PathParam("limit") int limit, @PathParam("offset") int offset){
        return mailService.getSentMails(senderId, limit, offset);
    }

    @GetMapping("/get/received/{receiverId}")
    public List<Map<String,Object>> getReceivedMails(@PathVariable("receiverId") Long receiverId, @PathParam("limit") int limit, @PathParam("offset") int offset){
        return mailService.getReceivedMails(receiverId, limit, offset);
    }

}
