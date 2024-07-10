package com.poec.projet_backend.domains.sse;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@Data
@AllArgsConstructor
public class EmitterDetails {
    private SseEmitter emitter;
    private Long id;
    private UUID uuid;
}
