package com.poec.projet_backend.domains.student;

import com.poec.projet_backend.domains.mentor.UserSlotService;
import com.poec.projet_backend.domains.notification.NotificationDTO;
import com.poec.projet_backend.domains.notification.NotificationRepository;
import com.poec.projet_backend.domains.notification.NotificationService;
import com.poec.projet_backend.domains.notification.NotificationType;
import com.poec.projet_backend.domains.reservation.*;
import com.poec.projet_backend.domains.slot.Slot;
import com.poec.projet_backend.domains.slot.SlotDTO;
import com.poec.projet_backend.domains.slot.SlotRepository;
import com.poec.projet_backend.user_app.UserApp;
import com.poec.projet_backend.user_app.UserAppRepository;
import com.poec.projet_backend.user_app.UserAppService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Service
public class StudentReservationService {
    private final UserAppService userAppService;
    private final NotificationRepository notificationRepository;
    @PersistenceContext
    private EntityManager entityManager;

    private final StudentRepository studentRepository;
    private final ReservationRepository reservationRepository;
    private final SlotRepository slotRepository;
    private final UserSlotService userSlotService;
    private final UserAppRepository userAppRepository;
    private final NotificationService notificationService;

    @Transactional
    public ReservationDTO create(ReservationDTO reservationDTO) throws Exception {
        try {
            if(reservationDTO.getStudentId() != null) {
                var student = studentRepository.findById(reservationDTO.getStudentId());
                var slot = slotRepository.findById(reservationDTO.getSlotId());
                if(student.isPresent() && slot.isPresent() && !slot.get().isBooked()) {
                    Reservation reservation = Reservation.builder()
                            .slot(slot.get())
                            .subject(reservationDTO.getSubject())
                            .message(reservationDTO.getMessage())
                            .details(reservationDTO.getDetails())
                            .student(student.get())
                            .build();
                    Reservation newReservation = reservationRepository.save(reservation);
                    // entityManager.flush();
                    UserApp user = userAppRepository.findById(slot.get().getMentor().getId()).orElseThrow(()->new RuntimeException("user not found"));
                    slot.get().setBooked(true);
                    slotRepository.save(slot.get());
                    // entityManager.flush();
                    NotificationDTO notif = NotificationDTO.builder()
                            .userId(user.getId())
                            .emittedAt(LocalDateTime.now())
                            .message("Reservation : " + student.get().getFirstname() + " " + student.get().getLastname() + ", créneau : " + slot.get().getDateBegin())
                            .type(NotificationType.BOOKING)
                            .build();
                    notificationService.createNotification(notif);
                    entityManager.flush();
                    return ReservationDTO.toDTO(reservationRepository.save(reservation));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream().map(ReservationDTO::toDTO).toList();
    }
    public List<ReservationDTO> getReservationsByStudentId(Long studentId) {
        return reservationRepository.findReservationsByStudentId(studentId).stream().map(ReservationDTO::toDTO).toList();
    }

    public Map<String, Object> getAllReservationByStudentIdInfosUpcoming(Long studentId, int perPage, int offset)
    {
        try{
            LocalDateTime time = LocalDateTime.now();
            System.out.println("time now " +time);
            var results = reservationRepository.findReservationByStudentIdInfosUpComing(studentId, time,offset, perPage );
            results.forEach(reservation -> System.out.println(reservation.toString()));
            if(results.isEmpty()) {
                Map<String, Object> result = new HashMap<>();
                result.put("reservations",new ArrayList<>());
                result.put("total",0);
                return result;
            }
            var res = results.stream().map(ele -> ResponseReservationForMentor.builder()
                    .dateBegin(((Timestamp) ele.get("dateBegin")).toLocalDateTime())
                    .dateEnd(((Timestamp) ele.get("dateEnd")).toLocalDateTime())
                    .firstname((String) ele.get("firstname"))
                    .lastname((String) ele.get("lastname"))
                    .title((String) ele.get("title"))
                    .imgUrl((String) ele.get("imgUrl"))
                    .subject((String) ele.get("subject"))
                    .message((String) ele.get("message"))
                    .details((String) ele.get("details"))
                    .id((Long) ele.get("id"))
                    .userId((Long) ele.get("userId"))
                    .mentorUserId((Long) ele.get("mentorUserId"))
                    .reservationId((Long) ele.get("reservationId"))
                    .studentId((Long) ele.get("studentId"))
                    .slotId((Long) ele.get("slotId"))
                    .isBooked((boolean) ele.get("booked"))
                    .isVisio((boolean) ele.get("visio"))
                    .build()).toList();
            Map<String, Object> result = new HashMap<>();
            result.put("reservations",res);
            result.put("total",(Long) results.get(0).get("totalCount"));
            System.out.println("result " + result.toString());
            return result;
        }catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("reservations",new ArrayList<>());
            result.put("total",0);
            return result;
        }
    }

    public Map<String, Object> getAllReservationByStudentIdInfosHistory(Long studentId, int perPage, int offset)
    {
        try {
            LocalDateTime time = LocalDateTime.now();
            System.out.println("time now " +time);
            var results = reservationRepository.findReservationByStudentInfosHistory(studentId, time,offset, perPage );
            results.forEach(reservation ->{ System.out.println(reservation.get("firstname")+" "+reservation.get("lastname"));
                System.out.println("booked " + reservation.get("booked")+"  visio "+reservation.get("visio"));
                System.out.println("details " + reservation.get("details")+"  visio "+reservation.get("details"));
            });
            if(results.isEmpty()) {
                Map<String, Object> result = new HashMap<>();
                result.put("reservations",new ArrayList<>());
                result.put("total",0);
                return result;
            }
            var res = results.stream().map(ele -> ResponseReservationForMentor.builder()
                    .dateBegin(((Timestamp) ele.get("dateBegin")).toLocalDateTime())
                    .dateEnd(((Timestamp) ele.get("dateEnd")).toLocalDateTime())
                    .firstname((String) ele.get("firstname"))
                    .lastname((String) ele.get("lastname"))
                    .title((String) ele.get("title"))
                    .imgUrl((String) ele.get("imgUrl"))
                    .subject((String) ele.get("subject"))
                    .message((String) ele.get("message"))
                    .details((String) ele.get("details"))
                    .id((Long) ele.get("id"))
                    .userId((Long) ele.get("userId"))
                    .mentorUserId((Long) ele.get("mentorUserId"))
                    .reservationId((Long) ele.get("reservationId"))
                    .studentId((Long) ele.get("studentId"))
                    .slotId((Long) ele.get("slotId"))
                    .isBooked((boolean) ele.get("booked"))
                    .isVisio((boolean) ele.get("visio"))
                    .build()).toList();
            Map<String, Object> result = new HashMap<>();
            result.put("reservations",res);
            result.put("total",(Long) results.get(0).get("totalCount"));
            return result;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Map<String, Object> result = new HashMap<>();
            result.put("reservations",new ArrayList<>());
            result.put("total",0);
            return result;
        }

        //return reservationRepository.findReservationByStudentInfosHistory(studentId, time,offset, perPage );
    }

    public Map<String, Object> getAllReservationByMentorIdInfosUpComing(Long mentorId, int perPage, int offset)
    {
        try {
            LocalDateTime time = LocalDateTime.now();
            System.out.println("time now " +time);
            var results = reservationRepository.findReservationInfosByMentorIdUpComing(mentorId, time,offset, perPage );
            if(results.size() > 0){
                var res = results.stream().map(ele -> ResponseReservationForMentor.builder()
                        .dateBegin(((Timestamp) ele.get("dateBegin")).toLocalDateTime())
                        .dateEnd(((Timestamp) ele.get("dateEnd")).toLocalDateTime())
                        .firstname((String) ele.get("firstname"))
                        .lastname((String) ele.get("lastname"))
                        .title((String) ele.get("title"))
                        .imgUrl((String) ele.get("imgUrl"))
                        .subject((String) ele.get("subject"))
                        .message((String) ele.get("message"))
                        .details((String) ele.get("details"))
                        .id((Long) ele.get("id"))
                        .userId((Long) ele.get("userId"))
                        .mentorUserId((Long) ele.get("mentorUserId"))
                        .reservationId((Long) ele.get("reservationId"))
                        .studentId((Long) ele.get("studentId"))
                        .slotId((Long) ele.get("slotId"))
                        .isBooked((boolean) ele.get("booked"))
                        .isVisio((boolean) ele.get("visio"))
                        .build()).toList();
                Map<String, Object> result = new HashMap<>();
                result.put("reservations",res);
                result.put("total",(Long) results.get(0).get("totalCount"));
                return result;
            }
            Map<String, Object> result = new HashMap<>();
            result.put("reservations",new ArrayList<>());
            result.put("total",0);
            return result;
        }catch (Exception ex)
        {
            ex.printStackTrace();
            Map<String, Object> result = new HashMap<>();
            result.put("reservations",new ArrayList<>());
            result.put("total",0);
            return result;
        }

    }

    public Map<String, Object> getAllReservationByMentorIdInfosHistory(Long mentorId, int perPage, int offset)
    {
        try {
            LocalDateTime time = LocalDateTime.now();
            var results = reservationRepository.findReservationInfosByMentorIdHistory(mentorId, time,offset, perPage );
            if(results.size() > 0 ){
                var res = results.stream().map(ele -> ResponseReservationForMentor.builder()
                        .dateBegin(((Timestamp) ele.get("dateBegin")).toLocalDateTime())
                        .dateEnd(((Timestamp) ele.get("dateEnd")).toLocalDateTime())
                        .firstname((String) ele.get("firstname"))
                        .lastname((String) ele.get("lastname"))
                        .title((String) ele.get("title"))
                        .imgUrl((String) ele.get("imgUrl"))
                        .subject((String) ele.get("subject"))
                        .message((String) ele.get("message"))
                        .details((String) ele.get("details"))
                        .id((Long) ele.get("id"))
                        .userId((Long) ele.get("userId"))
                        .mentorUserId((Long) ele.get("mentorUserId"))
                        .reservationId((Long) ele.get("reservationId"))
                        .studentId((Long) ele.get("studentId"))
                        .slotId((Long) ele.get("slotId"))
                        .isBooked((boolean) ele.get("booked"))
                        .isVisio((boolean) ele.get("visio"))
                        .build()).toList();
                Map<String, Object> result = new HashMap<>();
                result.put("reservations",res);
                result.put("total",(Long) results.get(0).get("totalCount"));
                return result;

            }
            Map<String, Object> result = new HashMap<>();
            result.put("reservations",new ArrayList<>());
            result.put("total",0);
            return result;

        }catch (Exception ex){
            ex.printStackTrace();
            Map<String, Object> result = new HashMap<>();
            result.put("reservations",new ArrayList<>());
            result.put("total",0);
            return result;
        }

        // return reservationRepository.findReservationInfosByMentorIdHistory(mentorId, time,offset, perPage );
    }

    @Transactional
    public Map<String, Object> delete(Long reservationId) {
        try {
            var reservation = reservationRepository.findById(reservationId);
            Long slotId = reservation.get().getSlot().getId();
            var slot1 = slotRepository.findById(slotId).orElseThrow(()->new RuntimeException("slot not found"));
            System.out.println("slot id " + slotId);
            reservationRepository.deleteById(reservationId);
            entityManager.flush();
            System.out.println("slot id " + slotId);
            userSlotService.freeSlot(slotId);
            entityManager.flush();
            System.out.println("slot id " + slotId);
            // var slot = slotRepository.findById(slotId).orElseThrow(()->new RuntimeException("slot not founded"));
            UserApp user = userAppRepository.findById(slot1.getMentor().getId()).orElseThrow(()->new RuntimeException("user not found"));

            NotificationDTO notif = NotificationDTO.builder()
                    .userId(user.getId())
                    .emittedAt(LocalDateTime.now())
                    .message("Annulation : " + reservation.get().getStudent().getFirstname() + " " + reservation.get().getStudent().getLastname() + ", créneau : " + slot1.getDateBegin())
                    .type(NotificationType.BOOKING)
                    .build();
            notificationRepository.save(NotificationDTO.toNotification(notif, user));
            Map<String, Object> result = new HashMap<>();
            result.put("message ", "Reservation annulé");
            result.put("success",true);
            return result;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Map<String, Object> result = new HashMap<>();
            result.put("message ", ex.getMessage());
            result.put("success",false);
            return result;
        }

    }

    @Transactional
    public Map<String, Object> deleteReservationAndSlot(Long reservationId) {
        try {
            var reservation = reservationRepository.findById(reservationId);
            var slotId = reservation.get().getId();
            reservationRepository.deleteById(reservationId);
            System.out.println("slot id " + slotId);
            slotRepository.deleteById(slotId);
            Map<String, Object> result = new HashMap<>();
            result.put("message ", "Reservation annulé");
            result.put("success",true);
            return result;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Map<String, Object> result = new HashMap<>();
            result.put("message ", ex.getMessage());
            result.put("success",false);
            return result;
        }

    }

    public Reservation update(Long reservationId,int first,  String message) {
        var reservation = reservationRepository.findById(reservationId);
        if (reservation.isPresent()) {
            reservation.get().setMessage(message);
            return reservationRepository.save(reservation.get());
        }
        return null;
    }

    public SlotDTO BookSlot(Long studentId, Long slotId, String subject) {

        return null;
    }

}
