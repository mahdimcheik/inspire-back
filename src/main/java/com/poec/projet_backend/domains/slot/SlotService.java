package com.poec.projet_backend.domains.slot;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Data
@Service
public class SlotService {
    private SlotRepository slotRepository;



    public List<Slot> getSlots() {
        //return slotRepository.findAllActiveSlotNative();
        return slotRepository.findAll();
    }



}
