package com.poec.projet_backend.user_app;


import com.poec.projet_backend.domains.formation.Formation;
import com.poec.projet_backend.domains.formation.FormationDTO;
import com.poec.projet_backend.domains.formation.FormationRepository;
import com.poec.projet_backend.domains.formation.ResponseFormation;
import com.poec.projet_backend.domains.mentor.Mentor;
import com.poec.projet_backend.domains.mentor.MentorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.text.Format;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class UserFormationService {
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

            formationRepository.save(newFormation);
            return formationRepository.findAllByUserId(formation.getUserId()).stream()
                    .map((formation1 -> FormationDTO.builder().city(formation1.getCity())
                            .country(formation1.getCountry())
                            .dateEnd(formation1.getDateEnd())
                            .title(formation1.getTitle())
                            .dateBegin(formation1.getDateBegin())
                            .company(formation1.getCompany())
                            .userId(userApp.getId())
                            .build()
                    )).toList();
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
            if(formation != null){
                formationRepository.delete(formation);
                List<FormationDTO> formations = formationRepository.findAllByUserId(userId).stream().map(ele -> FormationDTO.from(ele)).toList();
                return ResponseFormation.builder()
                        .formations(formations)
                        .message("done")
                        .success(true)
                        .build();
            }
            return ResponseFormation.builder()
                    .formations(new ArrayList<>())
                    .message("failed")
                    .success(false)
                    .build();
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
