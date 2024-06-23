package com.poec.projet_backend.domains.sse;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseService {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<SseEmitter>();
    private final Map<String, SseEmitter> emittersMap = new HashMap<String, SseEmitter>();

    public void addEmitter(SseEmitter emitter,Long id) {

        emittersMap.remove("" + id);
        emittersMap.put("" + id, emitter);
        emitter.onCompletion(() -> {
            System.out.println("complete");
            emitters.remove(emitter);
        });
        emitter.onTimeout(() -> emitters.remove(emitter));
    }

    // @Scheduled(fixedRate = 1000)
    public void sendEvents() {
        emittersMap.forEach((emitter, value) ->  {
                    try {
                        value.send("lol " + System.currentTimeMillis());
                    } catch (IOException e) {
                        value.complete();
                        emittersMap.remove(emitter);
                    }
                }
        );
    }

    public void sendEvents(Long id) {
        try {
            emittersMap.get(id).send(" Message recu à  " + System.currentTimeMillis()  );
        } catch (IOException e) {
            emittersMap.get(id).complete();
            emittersMap.remove(id);
        }
    }
}