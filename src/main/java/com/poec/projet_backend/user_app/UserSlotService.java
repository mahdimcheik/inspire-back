package com.poec.projet_backend.user_app;


import com.poec.projet_backend.domains.mentor.Mentor;
import com.poec.projet_backend.domains.mentor.MentorDTO;
import com.poec.projet_backend.domains.mentor.MentorRepository;
import com.poec.projet_backend.domains.reservation.Reservation;
import com.poec.projet_backend.domains.slot.Slot;
import com.poec.projet_backend.domains.slot.SlotDTO;
import com.poec.projet_backend.domains.slot.SlotRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@Data
@Service
public class UserSlotService {
    private final UserAppRepository userRepository;
    private final SlotRepository slotRepository;
    private final MentorRepository mentorRepository;

    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public List<SlotDTO> getSlotByMentorId(Long mentorId) {
        return slotRepository.findAllByMentorId(mentorId).stream().map(SlotDTO::fromEntity).toList();
    }

    public SlotDTO addSlot(Long mentorId, SlotDTO slotDTO) {
        Mentor mentor = mentorRepository.findById(mentorId).get();
        Reservation reservation = new Reservation();
        Slot slot = SlotDTO.toEntity(slotDTO,mentor, reservation);

        return SlotDTO.fromEntity(slotRepository.save(slot));
    }

    public List<Slot> getSlotByUserIdStartToEnd(Date startDate, Date endDate) {
        LocalDateTime startDateTime = convertDateToLocalDateTime(startDate);
        LocalDateTime endDateTime = convertDateToLocalDateTime(endDate);
        return slotRepository.findAllActiveSlotNative(startDateTime, endDateTime);
    }

    public SlotDTO updateSlot(SlotDTO slotDTO) {
        Slot slot = slotRepository.findById(slotDTO.getId()).get();
        if(slot != null) {
            Slot newSlot = Slot.builder()
                    .id(slotDTO.getId())
                    .dateBegin(slotDTO.getDateBegin())
                    .dateEnd(slotDTO.getDateEnd())
                    .visio(slotDTO.isVisio())
                    .mentor(slot.getMentor())
                    .build();
            if (slot.getReservation() != null) {
                newSlot.setReservation(slot.getReservation());
            }
        }
        return SlotDTO.fromEntity(slotRepository.save(slot));

    }
//
//    public List <SlotDTO> addSlotMentor(SlotDTO slot) {
////        try {
////            UserApp userApp = userRepository.findById(slot .getUserId()).orElseThrow(() -> new RuntimeException());
////
////Slot newSlot = Slot.builder().dateBegin(slot.getDateBegin()).dateEnd(slot.getDateEnd()).visio(slot.isVisio()).isBooked(false).mentor()
////
////            slotRepository.save(slot);
////            return slotRepository.findAllByUserId( slot.getUserId());
////        } catch (Exception e) {
////            throw new RuntimeException(e.getMessage());
////
////        }
////    }
////
////    public List<Slot> getSlotByUserId(Long userId) {
////        return slotRepository.findAllByUserId(userId);
////    }
//
////    public Slot deleteSlot(Long slotId) {
////        Slot slot = slotRepository.findById(slotId).orElseThrow(() -> new RuntimeException());
////        slotRepository.delete(slot);
////        return slotRepository.findBySlotId(slotId);
////    }
}