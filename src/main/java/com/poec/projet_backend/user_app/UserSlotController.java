package com.poec.projet_backend.user_app;


import lombok.Data;
import org.springframework.web.bind.annotation.*;


@RestController
@Data
@RequestMapping("/user/slot")
public class UserSlotController {
    private final UserSlotService userSlotService;

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
