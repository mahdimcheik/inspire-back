package com.poec.projet_backend.domains.mentor;


import com.poec.projet_backend.domains.reservation.RangeDate;
import com.poec.projet_backend.domains.slot.SlotDTO;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


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
        return userSlotService.getSlotByUserIdStartToEnd(mentorId,range.getStart() ,range.getEnd()).stream().map(SlotDTO::fromEntity).toList();
    }

    @PostMapping("/add")
    public SlotDTO addUserSlot(@RequestBody SlotDTO slotDTO) {
        return userSlotService.addSlot(slotDTO.getMentorId(), slotDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserSlot(@PathVariable Long id) {
        userSlotService.deleteSlot(id);
    }

    @PutMapping("/update")
    public SlotDTO updateUserSlot(@RequestBody SlotDTO slotDTO) {
        return userSlotService.updateSlot(slotDTO);
    }

    @PostMapping("/test/{mentorId}")
    public ResponseEntity<List<Map<String, Object>>> test(@PathVariable Long mentorId, @RequestBody RangeDate date) {
        return new ResponseEntity<>(userSlotService.test(mentorId,date.getStart(), date.getEnd()), HttpStatus.ACCEPTED);
    }

}
