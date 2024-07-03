package com.poec.projet_backend.domains.slot;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@RequestMapping("test")
public class SlotController {
    private final SlotService slotService;

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        slotService.deleteSlot(id);
        return ResponseEntity.ok().build();
    }
}
