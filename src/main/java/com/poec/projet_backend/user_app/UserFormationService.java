package com.poec.projet_backend.user_app;


import com.poec.projet_backend.domains.formation.Formation;
import com.poec.projet_backend.domains.formation.FormationDTO;
import com.poec.projet_backend.domains.formation.FormationRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class UserFormationService {
    private final UserAppRepository userRepository;
    private final FormationRepository formationRepository;

    public List<FormationDTO> addUserFormation(FormationDTO formation) {
        try {

            UserApp userApp =  userRepository.findById(formation.getUserId()).orElseThrow(() ->  new RuntimeException() );
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
}
