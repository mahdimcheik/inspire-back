package com.poec.projet_backend.domains.sse;

import io.jsonwebtoken.impl.crypto.MacProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sse")
@Data
public class SseController {
    private final SseService sseService;
    private SseEmitter emitter;
    private Long lastId = 0L;

    @GetMapping("/subscribe/{id}/{token}")
    public SseEmitter subscribe(@PathVariable Long id,@PathVariable String token) throws IOException {
        SseEmitter emitter = new SseEmitter(6000000L);
        try {
           System.out.println("emitter started " +  token);
            Map<String, String> response = new HashMap<>();
            response.put("token",  "Connexion");
            emitter.send(SseEmitter.event()
                    .name("message")
                    .id("" + token)
                    .data(response));
            sseService.addEmitter(emitter, id, token);
            return emitter;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/invoke")
    public String invokeAll() {
        sseService.sendEvents();
        return "Started";
    }

    @GetMapping("/invoke/{id}")
    public String invokeAll(@PathVariable Long id) throws IOException {
        sseService.sendEvents(id);
        return "Started";
    }
}