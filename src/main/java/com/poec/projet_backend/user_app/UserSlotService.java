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
            UserApp userApp = userRepository.findById(slot .getUserId()).orElseThrow(() -> new RuntimeException());

            slot.setBooked(false);

            slotRepository.save(slot);
            return slotRepository.findAllByUserId( slot.getUserId());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        }
    }

    public List<Slot> getSlotByUserId(Long userId) {
        return slotRepository.findAllByUserId(userId);
    }
}