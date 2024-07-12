package com.poec.projet_backend.domains.sse;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.yaml.snakeyaml.emitter.Emitter;

import java.util.*;

@Data
@AllArgsConstructor
public class EmitterDetails {
    private Map<String, SseEmitter> tokensEmitters = new HashMap<>();
    private Long id;

    @Override
    public String toString() {
        final String[] res = {"event emitter "};
        getTokensEmitters().forEach((key, value) -> {
            res[0] += "\n key: " + key + " , emitter : " + value.toString();
        });
        return res[0];
    }
}
