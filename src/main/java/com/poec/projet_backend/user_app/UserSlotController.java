package com.poec.projet_backend.user_app;


import com.poec.projet_backend.domains.reservation.RangeDate;
import com.poec.projet_backend.domains.slot.Slot;
import com.poec.projet_backend.domains.slot.SlotDTO;
import com.poec.projet_backend.domains.slot.SlotRepository;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@Data
@RequestMapping("/user/slot")
public class UserSlotController {
    private final UserSlotService userSlotService;

    @GetMapping("/get/{mentorId}")
    public List<SlotDTO> getUserSlots(@PathVariable Long mentorId) {
        return userSlotService.getSlotByMentorId(mentorId);
    }

    @PostMapping("/get/{mentorId}")
    public List<SlotDTO> getUserSlotsInRang(@PathVariable Long mentorId, @RequestBody RangeDate range ) {
        System.out.println("date " + range.toString());
        return userSlotService.getSlotByUserIdStartToEnd(range.getStart() ,range.getEnd()).stream().map(SlotDTO::fromEntity).toList();
    }

    @PostMapping("/add")
    public SlotDTO addUserSlot(@RequestBody SlotDTO slotDTO) {
        return userSlotService.addSlot(slotDTO.getMentorId(), slotDTO);
    }
    @PutMapping("/update")
    public SlotDTO updateUserSlot(@RequestBody SlotDTO slotDTO) {
        return userSlotService.updateSlot(slotDTO);
    }
//    @PostMapping("/add/{userId}")
//    public List<Slot> addSlotMentor(@RequestBody Slot slot, @PathVariable Long userId) {
//        Slot.builder().dateBegin(slot);
//        return userSlotService.addSlotMentor(slot);
//    }

//@GetMapping ("/{userId}")
//public List<Slot> getSlotByUserId(@PathVariable Long userId) {
//    return userSlotService.getSlotByUserId(userId);}

//@DeleteMapping ("/delete/{slotId}")
//public Slot deleteSlot(@PathVariable Long slotId) {
//    return userSlotService.deleteSlot(slotId);}


}
