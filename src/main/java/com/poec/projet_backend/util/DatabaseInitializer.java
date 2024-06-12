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

        createMentor(new MentorDTO(1L, "Marie", "Delo", "super dev", "Super mentorette", "no fking image", "git",
                "link", 1L));
        createMentor(new MentorDTO(2L, "Mathieu", "Dupont", "Data Scientist", "Expert en Data",
                "https://picsum.photos/200", "githubJean", "linkedinJean", 2L));
        createMentor(new MentorDTO(3L, "Mahdi", "Martin", "UX Designer", "Créateur d'Expérience",
                "https://picsum.photos/200", "githubAlice", "linkedinAlice", 3L));
        createMentor(new MentorDTO(4L, "Lucas", "Moreau", "DevOps", "Spécialiste en Infrastructure",
                "https://picsum.photos/200", "githubLucas", "linkedinLucas", 4L));
        createMentor(new MentorDTO(5L, "Emma", "Leroy", "Product Manager", "Gestionnaire de Produit",
                "https://picsum.photos/200", "githubEmma", "linkedinEmma", 5L));

        List<Long> mentorids = new ArrayList<>();
        createStudent(new StudentDTO(1L, "Marie", "Delaire", "Dev", "Super Mentorette", "https://picsum.photos/200",
                "github/mariedelaire", "linkedin/mariedelaire", 6L, mentorids));
        createStudent(new StudentDTO(2L, "Jean", "Dupont", "Data Scientist", "Expert en données",
                "https://picsum.photos/200", "github/jeandupont", "linkedin/jeandupont", 7L, mentorids));
        createStudent(new StudentDTO(3L, "Alice", "Martin", "UX Designer", "Créatrice d'expériences",
                "https://picsum.photos/200", "github/alicemartin", "linkedin/alicemartin", 8L, mentorids));
        createStudent(new StudentDTO(4L, "Luc", "Girard", "Backend Developer", "Maître du backend",
                "https://picsum.photos/200", "github/lucgirard", "linkedin/lucgirard", 9L, mentorids));
        createStudent(new StudentDTO(5L, "Sophie", "Leblanc", "Fullstack Developer", "Génie du Fullstack",
                "https://picsum.photos/200", "github/sophieleblanc", "linkedin/sophieleblanc", 10L, mentorids));

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

                ExperienceDTO exp = ExperienceDTO.builder()
                        .title("formation " + j)
                        .dateEnd(LocalDate.of(2010 + i, 1, 8))
                        .dateBegin(LocalDate.of(2021 + j, 1, 8))
                        .city("Bordeaux")
                        .userId((long) (i + 1))
                        .build();
                createExperience(exp);

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