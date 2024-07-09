package com.poec.projet_backend.domains.mentor;


import com.poec.projet_backend.domains.reservation.RangeDate;
import com.poec.projet_backend.domains.slot.Slot;
import com.poec.projet_backend.domains.slot.SlotDTO;
import com.poec.projet_backend.domains.slot.SlotResponseForMentorDTO;
import com.poec.projet_backend.user_app.UserApp;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@Data
@RequestMapping("/user/slot")
public class UserSlotController {
    private final UserSlotService userSlotService;

    @GetMapping("/get/{mentorId}")
    public List<Map<String,String>> getUserSlots(@PathVariable Long mentorId) {
        return userSlotService.getSlotByMentorId(mentorId);
    }

    @PostMapping("/get/{mentorId}")
    public List<Map<String, Object>> getUserSlotsInRang(@PathVariable Long mentorId, @RequestBody RangeDate range ) {
        System.out.println("date " + range.toString());
        return userSlotService.getSlotByUserIdStartToEnd(mentorId,range.getStart() ,range.getEnd());
    }

    @PostMapping("/add")
    public SlotDTO addUserSlot(@RequestBody SlotDTO slotDTO) {

        return userSlotService.addSlot(slotDTO.getMentorId(), slotDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserSlot(@PathVariable Long id) throws Exception {
        userSlotService.deleteSlot(id);
    }

    @PutMapping("/update")
    public SlotDTO updateUserSlot(@RequestBody SlotDTO slotDTO) {
        return userSlotService.updateSlot(slotDTO);
    }

    @PostMapping("/slots/{mentorId}/{studentId}")
    public ResponseEntity<List<Map<String, Object>>> test(@PathVariable Long mentorId,@PathVariable Long studentId, @RequestBody RangeDate date) {
        return new ResponseEntity<>(userSlotService.getSlotsforStudentByMentorId(mentorId, studentId, date.getStart(), date.getEnd()), HttpStatus.ACCEPTED);
    }
}
