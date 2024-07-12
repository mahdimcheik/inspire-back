package com.poec.projet_backend.domains.sse;

import com.poec.projet_backend.user_app.UserAppRepository;
import lombok.Data;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
@Service
public class SseService {
    private  Map<Long, EmitterDetails> emitters = new HashMap<>();
    private final UserAppRepository appRepository;
    private final UserAppRepository userAppRepository;

    public void addEmitter(SseEmitter emitter,Long id, String token) {
        try{
            var cible = emitters.get(id);
           if( cible != null){
               cible.getTokensEmitters().put(token, emitter);
               emitters.put(id, cible);
               printEmitters();
           }else {
               Map<String, SseEmitter> tokensEmitters = new HashMap<>();
               tokensEmitters.put(token, emitter);
               EmitterDetails newCible = new EmitterDetails(tokensEmitters,id);
               emitters.put(id, newCible);
               printEmitters();
           }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void printEmitters(){
        emitters.forEach((key, value) -> System.out.println("\nkey : " + key + " , emitter : " + emitters.get(key).toString()));
    }


    // @Scheduled(fixedRate = 1000)
    public void sendEvents() {
//        emitters.forEach((key, value) ->  {
//                    try {
//                        value.getEmitter().send("lol " + System.currentTimeMillis());
//                    } catch (IOException e) {
//                        value.getEmitter().complete();
//                        emitters.remove(value);
//                    }
//                }
//        );
    }

    public void sendEvents(Long id) throws IOException {
        //emitters.get(id).getEmitter().send("Notifications");
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);

        try{
            emitters.get(id).getTokensEmitters().forEach((token, emitter) -> {
                response.put("token", token);
                try {
                    emitter.send(response);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void removeEmitter(Long id, String token) {
        var cible = emitters.get(id);
        cible.getTokensEmitters().remove(token);
    }
}