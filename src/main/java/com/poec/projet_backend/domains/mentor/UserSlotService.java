package com.poec.projet_backend.domains.mentor;


import com.poec.projet_backend.domains.reservation.Reservation;
import com.poec.projet_backend.domains.reservation.WeekUtil;
import com.poec.projet_backend.domains.slot.Slot;
import com.poec.projet_backend.domains.slot.SlotDTO;
import com.poec.projet_backend.domains.slot.SlotRepository;
import com.poec.projet_backend.domains.slot.SlotResponseForMentorDTO;
import com.poec.projet_backend.user_app.UserAppRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Data
@Service
public class UserSlotService {
    @PersistenceContext
    private EntityManager entityManager;
    private final UserAppRepository userRepository;
    private final SlotRepository slotRepository;
    private final MentorRepository mentorRepository;
    public List<Map<String,String>> getSlotByMentorId(Long mentorId) {
        return slotRepository.findAllByMentorIdDetailed(mentorId); //.stream().map(SlotDTO::fromEntity).toList();
    }

    public SlotDTO addSlot(Long mentorId, SlotDTO slotDTO) {
        Mentor mentor = mentorRepository.findById(mentorId).get();
        Reservation reservation = new Reservation();
        Slot slot = SlotDTO.toEntity(slotDTO,mentor, reservation);
        var start =WeekUtil.convertLocalDateToDate( WeekUtil.getStartOfWeek(LocalDate.from(slotDTO.getDateBegin())));
        var end = WeekUtil.convertLocalDateToDate(WeekUtil.getEndOfWeek(LocalDate.from(slotDTO.getDateEnd())));
        System.out.println(start);
        System.out.println(end);

        return SlotDTO.fromEntity(slotRepository.save(slot));
    }

    public List<Map<String, Object>> getSlotByUserIdStartToEnd(Long mentorId, Date startDate, Date endDate) {
        LocalDateTime startDateTime =WeekUtil.convertDateToLocalDateTime(startDate);
        LocalDateTime endDateTime = WeekUtil.convertDateToLocalDateTime(endDate);
        System.out.println(mentorId);
        return slotRepository.findAllActiveUsersSlotsNative(mentorId ,startDateTime, endDateTime);
    }

    public SlotDTO updateSlot(SlotDTO slotDTO) {
        var slot = slotRepository.findById(slotDTO.getId());
        if(slot.isPresent()) {
            Slot newSlot = Slot.builder()
                    .id(slotDTO.getId())
                    .dateBegin(slotDTO.getDateBegin())
                    .dateEnd(slotDTO.getDateEnd())
                    .visio(slotDTO.isVisio())
                    .mentor(slot.get().getMentor()).build();
            return SlotDTO.fromEntity(slotRepository.save(newSlot));
        }
        return null;
    }

    @Transactional
    public SlotDTO freeSlot(Long slotId) {
        var oldSlot = slotRepository.findById(slotId) ;
        entityManager.flush();
        if(oldSlot.isPresent()) {
            Slot slot = oldSlot.get();
            slotRepository.delete(slot);
            slot.setReservation(null);
            var res = slotRepository.save(slot);
            System.out.println("success");
            return SlotDTO.fromEntity(res);
        }
        System.out.println("failed");
        return null;
    }

    public void deleteSlot(Long id) {
        Slot slot = slotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found with id " + id));
        slotRepository.delete(slot);
    }

    public List<Map<String, Object>> getSlotsforStudentByMentorId(Long mentorId, Long studentId,  Date startDate, Date endDate) {
        System.out.println("get mentor slot for student called ");
        LocalDateTime startDateTime =WeekUtil.convertDateToLocalDateTime(startDate);
        LocalDateTime endDateTime = WeekUtil.convertDateToLocalDateTime(endDate);
        LocalDateTime now = LocalDateTime.now();
        if(endDateTime.isBefore(now)) {
            System.out.println(" return none ");

            return new ArrayList<>(); }
        if(startDateTime.isBefore(now)) {
            System.out.println(" less than time now");

            startDateTime = now;
        }
        return slotRepository.getSlotsByMentorIdForStudent(  mentorId,studentId, startDateTime, endDateTime);
    }
}