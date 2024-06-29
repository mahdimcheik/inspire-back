package com.poec.projet_backend.domains.formation;

import com.poec.projet_backend.domains.mentor.MentorRepository;
import com.poec.projet_backend.user_app.UserApp;
import com.poec.projet_backend.user_app.UserAppRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ResponseFormation {
    List<FormationDTO> formations;
    String message;
    boolean success;

    @Data
    @Service
    public static class UserFormationService {
        private final UserAppRepository userRepository;
        private final FormationRepository formationRepository;
        private final MentorRepository mentorRepository;

        public List<FormationDTO> addUserFormation(FormationDTO formation) {
            try {
                System.out.println(" avant");
                UserApp userApp =  userRepository.findById(formation.getUserId()).orElseThrow(() ->  new RuntimeException() );
                System.out.println(" apres");

                Formation newFormation = new Formation();
                newFormation.setTitle(formation.getTitle());
                newFormation.setCompany(formation.getCompany());
                newFormation.setDateBegin(formation.getDateBegin());
                newFormation.setDateEnd(formation.getDateEnd());
                newFormation.setCity(formation.getCity());
                newFormation.setCountry(formation.getCountry());
                newFormation.setUser(userApp);


                formationRepository.save(newFormation);
                return formationRepository.findAllByUserId(formation.getUserId()).stream()
                        .map((exp)-> FormationDTO.builder().userId(newFormation.getUser().getId())
                                .title(exp.getTitle())
                                .company(exp.getCompany())
                                .dateBegin(exp.getDateBegin())
                                .dateEnd(exp.getDateEnd())
                                .city(exp.getCity())
                                .country(exp.getCountry())
                                .build()).toList();
            }
            catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        public List<FormationDTO> updateUserFormation(FormationDTO formation, Long id) {
            try {
                UserApp userApp =  userRepository.findById(formation.getUserId()).orElseThrow(() ->  new RuntimeException() );
                Formation newFormation = formationRepository.findById(id).orElseThrow(() ->  new RuntimeException() );

                newFormation.setTitle(formation.getTitle());
                newFormation.setCompany(formation.getCompany());
                newFormation.setDateBegin(formation.getDateBegin());
                newFormation.setDateEnd(formation.getDateEnd());
                newFormation.setCity(formation.getCity());
                newFormation.setCountry(formation.getCountry());
                newFormation.setUser(userApp);
                newFormation.setId(id);

                formationRepository.save(newFormation);
                return formationRepository.findAllByUserId(formation.getUserId()).stream()
                        .map(FormationDTO::fromEntity).toList();
            }
            catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }

        }

        public ResponseFormation delete(Long id) {
            try {
                Formation formation = formationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity with id " + id + " cannot be found"));
                Long userId = formation.getUser().getId();
                formationRepository.delete(formation);

                    formationRepository.delete(formation);
                    List<FormationDTO> formations = formationRepository.findAllByUserId(userId).stream().map(ele -> FormationDTO.fromEntity(ele)).toList();
                    return builder()
                            .formations(formations)
                            .message("done")
                            .success(true)
                            .build();


            } catch (EntityNotFoundException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
