package com.poec.projet_backend.domains.sse;

import com.poec.projet_backend.user_app.UserAppRepository;
import lombok.Data;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
@Service
public class SseService {
    private final Map<Long,SseEmitter> emitters = new HashMap<Long, SseEmitter>();
    private final UserAppRepository appRepository;
    private final UserAppRepository userAppRepository;

    public void addEmitter(SseEmitter emitter,Long id) {

        //var user = userAppRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
        emitters.remove(id);
        emitters.put(id,emitter);
        emitter.onCompletion(() -> {
            System.out.println("complete");
            emitters.remove(emitter);
        });
        emitter.onTimeout(() -> emitters.remove(emitter));
        System.out.println("emitters counts : " + emitters.size());
    }



    // @Scheduled(fixedRate = 1000)
    public void sendEvents() {
        emitters.forEach((key,value) ->  {
                    try {
                        value.send("lol " + System.currentTimeMillis());
                    } catch (IOException e) {
                        value.complete();
                        emitters.remove(key);
                    }
                }
        );
    }

    public void sendEvents(Long id) throws IOException {
        emitters.get(id).send("Notifications");

    }
}