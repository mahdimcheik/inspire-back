package com.poec.projet_backend.user_app;

import com.poec.projet_backend.domains.slot.Slot;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/user/slot")
public class UserSlotController {
private final UserSlotService userSlotService;

@PostMapping ("/add/{userId}")
public List<Slot> addSlotMentor(@RequestBody Slot slot, @PathVariable int userId) {
    slot.setUserId(userId);
    return userSlotService.addSlotMentor(slot);



}}
