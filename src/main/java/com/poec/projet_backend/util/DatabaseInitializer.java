package com.poec.projet_backend.util;

import com.poec.projet_backend.domains.experience.ExperienceDTO;
import com.poec.projet_backend.domains.formation.FormationDTO;
import com.poec.projet_backend.domains.language.Language;
import com.poec.projet_backend.domains.language.LanguageRepository;
import com.poec.projet_backend.domains.mentor.MentorDTO;
import com.poec.projet_backend.domains.mentor.MentorService;
import com.poec.projet_backend.domains.skill.Skill;
import com.poec.projet_backend.domains.skill.SkillRepository;
import com.poec.projet_backend.domains.student.StudentDTO;
import com.poec.projet_backend.domains.student.StudentService;
import com.poec.projet_backend.user_app.*;
import com.poec.projet_backend.user_app.Role;
import com.poec.projet_backend.user_app.UserApp;
import com.poec.projet_backend.user_app.UserAppRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final UserAppRepository userAppRepository;
    private final PasswordEncoder passwordEncoder;
    private final LanguageRepository languageRepository;
    private final SkillRepository skillRepository;
    private final MentorService mentorService;
    private final UserFormationService userFormationService;
    private final StudentService studentService;
    private final UserExperienceService userExperienceService;
    private final UserLanguageService userLanguageService;
    private final UserSkillService userSkillService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // if(this.userAppRepository.findByEmail("admin@admin.com").isEmpty()) {
        this.createUser("mentor1@gmail.com", "1234", Role.MENTOR);
        this.createUser("mentor2@gmail.com", "1234", Role.MENTOR);
        this.createUser("mentor3@gmail.com", "1234", Role.MENTOR);
        this.createUser("mentor4@gmail.com", "1234", Role.MENTOR);
        this.createUser("mentor5@gmail.com", "1234", Role.MENTOR);
        this.createUser("mentor6@gmail.com", "1234", Role.MENTOR);
        this.createUser("mentor7@gmail.com", "1234", Role.MENTOR);
        this.createUser("mentor8@gmail.com", "1234", Role.MENTOR);
        this.createUser("mentor9@gmail.com", "1234", Role.MENTOR);

        this.createUser("student1@gmail.com", "1234", Role.STUDENT);
        this.createUser("student2@gmail.com", "1234", Role.STUDENT);
        this.createUser("student3@gmail.com", "1234", Role.STUDENT);
        this.createUser("student4@gmail.com", "1234", Role.STUDENT);
        this.createUser("student5@gmail.com", "1234", Role.STUDENT);

        createLanguage("Francais");
        createLanguage("Deutsch");
        createLanguage("Arabe");
        createLanguage("Chinois");
        createLanguage("Japonais");

        createSkill("Java");
        createSkill("Python");
        createSkill("C++");
        createSkill("C#");
        createSkill("Javascript");
        createSkill("Angular");
        createSkill("React");
        createSkill("Php");

        createMentor(new MentorDTO(1L, "Marie", "Delo", "super dev", "Super mentorette",
                "http://localhost:8080/images/marieD.webp", "git", "link", 1L));
        createMentor(new MentorDTO(2L, "Mathieu", "Dupont", "Developpeur Web Java / Angular", "Expert en Java, je suis également compétent en Angular et j'ai un interet particulier pour le front ! ",
                "http://localhost:8080/images/mat.jpg", "githubJean", "linkedinJean", 2L));
        createMentor(new MentorDTO(3L, "Adam", "Martin", "UX Designer", "Créateur d'Expérience",
                "http://localhost:8080/images/AdamC.jpg", "githubAlice", "linkedinAlice", 3L));
        createMentor(new MentorDTO(4L, "Raph", "Moreau", "DevOps", "Spécialiste en Infrastructure",
                "http://localhost:8080/images/raph.jpeg", "githubLucas", "linkedinLucas", 4L));
        createMentor(new MentorDTO(5L, "Fred", "Leroy", "Product Manager", "Gestionnaire de Produit",
                "http://localhost:8080/images/fred.webp", "githubEmma", "linkedinEmma", 5L));
        createMentor(new MentorDTO(6L, "Pilou", "Treuh", "CTO", "Chef d'agence Tech",
                "http://localhost:8080/images/pilou.jpeg", "githubEmma", "linkedinEmma", 6L));
        createMentor(new MentorDTO(7L, "Orore", "Valixe", "Experte Debug", "Vous êtes ou ??? ",
                "http://localhost:8080/images/AuroreC.jpg", "githubEmma", "linkedinEmma", 7L));
        createMentor(new MentorDTO(8L, "Angeline", "Leroy", "Développeuse Angular Experte", "Gestionnaire de Produit",
                "http://localhost:8080/images/Angeline.jpeg", "githubEmma", "linkedinEmma", 8L));
        createMentor(new MentorDTO(9L, "Nassime", "Lemoulin", "Expert Package NPM", "Gestionnaire de package",
                "http://localhost:8080/images/NassimeC.jpg", "githubEmma", "linkedinEmma", 9L));

        List<Long> mentorids = new ArrayList<>();
        createStudent(new StudentDTO(1L, "Mahdi", "Mcheik", "Developpeur Junior", "J'ai envie d'apprendre !", "http://localhost:8080/images/mahdi.jpg",
                "github/mariedelaire", "linkedin/mariedelaire", 10L, mentorids));
        createStudent(new StudentDTO(2L, "Jean", "Dupont", "Data Scientist", "Expert en données",
                "https://picsum.photos/200", "github/jeandupont", "linkedin/jeandupont", 11L, mentorids));
        createStudent(new StudentDTO(3L, "Alice", "Martin", "UX Designer", "Créatrice d'expériences",
                "https://picsum.photos/200", "github/alicemartin", "linkedin/alicemartin", 12L, mentorids));
        createStudent(new StudentDTO(4L, "Luc", "Girard", "Backend Developer", "Maître du backend",
                "https://picsum.photos/200", "github/lucgirard", "linkedin/lucgirard", 13L, mentorids));
        createStudent(new StudentDTO(5L, "Sophie", "Leblanc", "Fullstack Developer", "Génie du Fullstack",
                "https://picsum.photos/200", "github/sophieleblanc", "linkedin/sophieleblanc", 14L, mentorids));

        addExperiencesForUser1();
        addExperiencesForUser2();

        // formation
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                String title = "formation " + j;
                FormationDTO formation = FormationDTO.builder()
                        .title("formation " + j)
                        .dateEnd(LocalDate.of(2010 + i, 1, 8))
                        .dateBegin(LocalDate.of(2021 + j, 1, 8))
                        .city("Bordeaux")
                        .userId((long) (i + 1))
                        .build();
                createFormation(formation);

                title = "formation " + j;
                /*
                 * ExperienceDTO exp = ExperienceDTO.builder()
                 * .title("experience " + j)
                 * .dateEnd(LocalDate.of(2010 + i, 1, 8))
                 * .dateBegin(LocalDate.of(2021 + j, 1, 8))
                 * .city("Bordeaux")
                 * .userId((long) (i + 1))
                 * .build();
                 * createExperience(exp);
                 */

            }
        }
        List<Language> languages = new ArrayList<>();
        languages.add(new Language(1L, "Francais"));
        languages.add(new Language(2L, "deutsch"));
        languages.add(new Language(3L, "arabe"));

        for (long i = 1; i <= 10; i++) {
            addUserLanguage(i, languages);
        }

        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill(1L, "Java"));
        skills.add(new Skill(2L, "Python"));
        skills.add(new Skill(3L, "C++"));

        for (long i = 1; i <= 10; i++) {
            addUserSkill(i, skills);
        }

    }

    private void addExperiencesForUser1() {

        ExperienceDTO experience1 = ExperienceDTO.builder()
                .title("Développeur Junior")
                .company("Company A")
                .dateBegin(LocalDate.of(2015, 1, 1))
                .dateEnd(LocalDate.of(2016, 1, 1))
                .city("Paris")
                .country("France")
                .userId(1L)
                .build();
        createExperience(experience1);

        ExperienceDTO experience2 = ExperienceDTO.builder()
                .title("Développeur Senior")
                .company("Company B")
                .dateBegin(LocalDate.of(2016, 2, 1))
                .dateEnd(LocalDate.of(2018, 2, 1))
                .city("Lyon")
                .country("France")
                .userId(1L)
                .build();
        createExperience(experience2);
    }

    private void addExperiencesForUser2() {

        ExperienceDTO experience1 = ExperienceDTO.builder()
                .title("Développeur Junior")
                .company("Company A")
                .dateBegin(LocalDate.of(2015, 1, 1))
                .dateEnd(LocalDate.of(2016, 3, 15))
                .city("Paris")
                .country("France")
                .userId(2L)
                .build();
        createExperience(experience1);
    }

    private void createAdmin() {
        UserApp admin = UserApp.builder()
                .email("admin@admin.com")
                .password(passwordEncoder.encode("admin"))
                .role("ROLE_" + Role.ADMIN)
                .build();

        this.userAppRepository.save(admin);
    }

    private void createUsers() {
        UserApp user1 = UserApp.builder()
                .email("user@user.com")
                .password(passwordEncoder.encode("user1"))
                .role("ROLE_" + Role.USER)
                .build();

        this.userAppRepository.save(user1);
    }

    private UserApp createUser(String email, String password, Role role) {
        UserApp user1 = UserApp.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(role.name())
                .build();

        this.userAppRepository.save(user1);
        return user1;
    }

    private void createLanguage(String newName) {
        Language language = new Language();
        language.setName(newName);
        languageRepository.save(language);
    }

    private void createSkill(String newName) {
        Skill skill = new Skill();
        skill.setName(newName);
        skillRepository.save(skill);
    }

    private void createMentor(MentorDTO newMentor) {
        mentorService.addMentorByUserId(newMentor);
    }

    private void createStudent(StudentDTO newStudent) {
        studentService.addStudentByUserId(newStudent);
    }

    private void createFormation(FormationDTO formation) {
        userFormationService.addUserFormation(formation);
    }

    private void createExperience(ExperienceDTO experience) {
        userExperienceService.addUserExperience(experience);
    }

    private void addUserLanguage(Long userId, List<Language> languages) {
        userLanguageService.updateUserLanguageList(userId, languages);
    }

    private void addUserSkill(Long userId, List<Skill> skills) {
        userSkillService.updateUserSkillList(userId, skills);
    }

}