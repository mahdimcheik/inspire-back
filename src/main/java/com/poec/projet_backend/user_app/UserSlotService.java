package com.poec.projet_backend.user_app;

import com.poec.projet_backend.domains.slot.Slot;
import com.poec.projet_backend.domains.slot.SlotRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class UserSlotService {
    private final UserAppRepository userRepository;
    private final SlotRepository slotRepository;


    public List <Slot> addSlotMentor(Slot slot) {
        try {
            UserApp userApp = userRepository.findById((long) slot .getUserId()).orElseThrow(() -> new RuntimeException());
            Slot newSlot = new Slot();
            newSlot.setDateBegin(slot.getDateBegin());
            newSlot.setDateEnd(slot.getDateEnd());
            newSlot.setVisio(slot.isVisio());
            newSlot.setUserId(slot.getUserId());
            newSlot.setBooked(false);
            slotRepository.save(newSlot);
            return slotRepository.findAllByUserId((long) slot.getUserId());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        }
    }
}