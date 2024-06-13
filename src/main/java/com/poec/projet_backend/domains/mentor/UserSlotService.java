package com.poec.projet_backend.domains.mentor;


import com.poec.projet_backend.domains.reservation.Reservation;
import com.poec.projet_backend.domains.reservation.WeekUtil;
import com.poec.projet_backend.domains.slot.Slot;
import com.poec.projet_backend.domains.slot.SlotDTO;
import com.poec.projet_backend.domains.slot.SlotRepository;
import com.poec.projet_backend.user_app.UserAppRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
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



    public List<SlotDTO> getSlotByMentorId(Long mentorId) {
        return slotRepository.findAllByMentorId(mentorId).stream().map(SlotDTO::fromEntity).toList();
    }

    public SlotDTO addSlot(Long mentorId, SlotDTO slotDTO) {
        Mentor mentor = mentorRepository.findById(mentorId).get();
        Reservation reservation = new Reservation();
        Slot slot = SlotDTO.toEntity(slotDTO,mentor, reservation);
        var start =WeekUtil.convertLocalDateToDate( WeekUtil.getStartOfWeek(LocalDate.from(slotDTO.getDateBegin())));
        var end = WeekUtil.convertLocalDateToDate(WeekUtil.getEndOfWeek(LocalDate.from(slotDTO.getDateEnd())));
        System.out.println(start);
        System.out.println(end);

        //return getSlotByUserIdStartToEnd(mentorId,start,end).stream().map(SlotDTO::fromEntity).toList();
        return SlotDTO.fromEntity(slotRepository.save(slot));
//        return slotRepository.findAllActiveUsersSlotsNative(mentorId ,
//                        WeekUtil.convertLocalDateToLocalDateTime(start),
//                        WeekUtil.convertLocalDateToLocalDateTime(end))
//                .stream().map(SlotDTO::fromEntity).toList();
    }

    public List<Slot> getSlotByUserIdStartToEnd(Long mentorId, Date startDate, Date endDate) {
        LocalDateTime startDateTime =WeekUtil.convertDateToLocalDateTime(startDate);
        LocalDateTime endDateTime = WeekUtil.convertDateToLocalDateTime(endDate);
        System.out.println(mentorId);
        return slotRepository.findAllActiveUsersSlotsNative(mentorId ,startDateTime, endDateTime);
        //return slotRepository.findAllActiveSlotNative(startDateTime, endDateTime);
    }

    public SlotDTO updateSlot(SlotDTO slotDTO) {
        var slot = slotRepository.findById(slotDTO.getId());
        if(slot.isPresent()) {
            Slot newSlot = Slot.builder()
                    .id(slotDTO.getId())
                    .dateBegin(slotDTO.getDateBegin())
                    .dateEnd(slotDTO.getDateEnd())
                    .visio(slotDTO.isVisio())
                    .mentor(slot.get().getMentor())
                    .build();
            if (slot.get().getReservation() != null) {
                newSlot.setReservation(slot.get().getReservation());
            }else
                newSlot.setReservation(null);
            return SlotDTO.fromEntity(slotRepository.save(newSlot));
        }
        return null;
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