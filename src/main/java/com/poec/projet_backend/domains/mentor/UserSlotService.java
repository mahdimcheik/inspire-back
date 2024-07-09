package com.poec.projet_backend.domains.mentor;


import com.poec.projet_backend.domains.notification.NotificationService;
import com.poec.projet_backend.domains.reservation.Reservation;
import com.poec.projet_backend.domains.reservation.WeekUtil;
import com.poec.projet_backend.domains.slot.Slot;
import com.poec.projet_backend.domains.slot.SlotDTO;
import com.poec.projet_backend.domains.slot.SlotRepository;
import com.poec.projet_backend.domains.slot.SlotResponseForMentorDTO;
import com.poec.projet_backend.domains.student.Student;
import com.poec.projet_backend.domains.student.StudentRepository;
import com.poec.projet_backend.domains.student.StudentReservationService;
import com.poec.projet_backend.user_app.UserApp;
import com.poec.projet_backend.user_app.UserAppRepository;
import com.poec.projet_backend.user_app.UserAppService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.NotFound;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Data
@Service
public class UserSlotService {
    private final UserAppService userAppService;
    @PersistenceContext
    private EntityManager entityManager;
    private final UserAppRepository userRepository;
    private final SlotRepository slotRepository;
    private final MentorRepository mentorRepository;
    private final StudentRepository studentRepository;
    private final UserAppRepository userAppRepository;
//    private final StudentReservationService studentReservationService;
    private final NotificationService  notificationService;

    public List<Map<String, String>> getSlotByMentorId(Long mentorId) {
        return slotRepository.findAllByMentorIdDetailed(mentorId); //.stream().map(SlotDTO::fromEntity).toList();
    }

    public void checkAuthenticityMentor(Long mentorId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserApp user = userAppRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        Mentor mentor = mentorRepository.findById(mentorId).get();
        if (user.getId() != mentor.getUser().getId()) {
            throw new RuntimeException("User is not the current user");
        }
    }

    public void checkAuthenticityStudent(Long studentId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserApp user = userAppRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        Student student = studentRepository.findById(studentId).get();
        if (user.getId() != student.getUser().getId()) {
            throw new RuntimeException("User is not the current user");
        }
    }
    public void checkAuthenticityUser(Long userId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserApp user = userAppRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getId() != userId) {
            throw new RuntimeException("User is not the current user");
        }
    }

    public SlotDTO addSlot(Long mentorId, SlotDTO slotDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Mentor mentor = mentorRepository.findById(mentorId).get();
        checkAuthenticityMentor(mentor.getId());

        Reservation reservation = new Reservation();
        Slot slot = SlotDTO.toEntity(slotDTO, mentor, reservation);
        System.out.println("slot dto recieved " + slotDTO.toString());
        var start = WeekUtil.convertLocalDateToDate(WeekUtil.getStartOfWeek(LocalDate.from(slotDTO.getDateBegin())));
        var end = WeekUtil.convertLocalDateToDate(WeekUtil.getEndOfWeek(LocalDate.from(slotDTO.getDateEnd())));
        System.out.println(start);
        System.out.println(end);

        return SlotDTO.fromEntity(slotRepository.save(slot));
    }

    public List<Map<String, Object>> getSlotByUserIdStartToEnd(Long mentorId, Date startDate, Date endDate) {
        LocalDateTime startDateTime = WeekUtil.convertDateToLocalDateTime(startDate);
        LocalDateTime endDateTime = WeekUtil.convertDateToLocalDateTime(endDate);
        System.out.println(mentorId);
        return slotRepository.findAllActiveUsersSlotsNative(mentorId, startDateTime, endDateTime);
    }

    public SlotDTO updateSlot(SlotDTO slotDTO) {
        checkAuthenticityMentor(slotDTO.getMentorId());
        var slot = slotRepository.findById(slotDTO.getId());
        if (slot.isPresent()) {
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

    public String dateFormatter(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM HH:mm");
        var res = date.format(formatter).split(" ");

        return  res[0] + " à " + res[1];
    }

    public void deleteSlot(Long id) throws Exception {
        Slot slot = slotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found with id " + id));
        checkAuthenticityMentor(slot.getMentor().getId());
        if(slot.getReservation() != null){
            var user = userAppRepository.findById(slot.getReservation().getStudent().getUser().getId()).orElseThrow(() -> new RuntimeException("Not found"));
            notificationService.add(user, slot.getMentor().getFirstname(), slot.getMentor().getLastname(),dateFormatter(slot.getDateBegin()), "Annulation");
        }
        slotRepository.delete(slot);
    }

    public List<Map<String, Object>> getSlotsforStudentByMentorId(Long mentorId, Long studentId, Date startDate, Date endDate) {
        System.out.println("get mentor slot for student called ");
        LocalDateTime startDateTime = WeekUtil.convertDateToLocalDateTime(startDate);
        LocalDateTime endDateTime = WeekUtil.convertDateToLocalDateTime(endDate);
        LocalDateTime now = LocalDateTime.now();
        if (endDateTime.isBefore(now)) {
            System.out.println(" return none ");

            return new ArrayList<>();
        }
        if (startDateTime.isBefore(now)) {
            System.out.println(" less than time now");

            startDateTime = now;
        }
        return slotRepository.getSlotsByMentorIdForStudent(mentorId, studentId, startDateTime, endDateTime);
    }
}