package com.poec.projet_backend.util;

import com.poec.projet_backend.domains.administrator.AdminDTO;
import com.poec.projet_backend.domains.administrator.AdminService;
import com.poec.projet_backend.domains.formation.FormationDTO;
import com.poec.projet_backend.domains.formation.ResponseFormation;
import com.poec.projet_backend.domains.language.Language;
import com.poec.projet_backend.domains.language.LanguageRepository;
import com.poec.projet_backend.domains.language.UserLanguageService;
import com.poec.projet_backend.domains.mentor.MentorDTO;
import com.poec.projet_backend.domains.mentor.MentorService;
import com.poec.projet_backend.domains.skill.Skill;
import com.poec.projet_backend.domains.skill.SkillRepository;
import com.poec.projet_backend.domains.skill.UserSkillService;
import com.poec.projet_backend.domains.student.StudentDTO;
import com.poec.projet_backend.domains.student.StudentService;
import com.poec.projet_backend.domains.superAdmin.SuperAdminDTO;
import com.poec.projet_backend.domains.superAdmin.SuperAdminService;
import com.poec.projet_backend.user_app.Role;
import com.poec.projet_backend.user_app.UserApp;
import com.poec.projet_backend.user_app.UserAppRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        private final ResponseFormation.UserFormationService userFormationService;
        private final StudentService studentService;
        private final UserLanguageService userLanguageService;
        private final UserSkillService userSkillService;
        private final AdminService adminService;
        private final SuperAdminService superAdminService;

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
                this.createUser("mentor10@gmail.com", "1234", Role.MENTOR);
                this.createUser("mentor11@gmail.com", "1234", Role.MENTOR);
                this.createUser("mentor12@gmail.com", "1234", Role.MENTOR);
                this.createUser("mentor13@gmail.com", "1234", Role.MENTOR);
                this.createUser("mentor14@gmail.com", "1234", Role.MENTOR);
                this.createUser("mentor15@gmail.com", "1234", Role.MENTOR);

                this.createUser("student1@gmail.com", "1234", Role.STUDENT);
                this.createUser("student2@gmail.com", "1234", Role.STUDENT);
                this.createUser("student3@gmail.com", "1234", Role.STUDENT);
                this.createUser("student4@gmail.com", "1234", Role.STUDENT);
                this.createUser("student5@gmail.com", "1234", Role.STUDENT);

                this.createUser("admin1@gmail.com", "1234", Role.ADMIN);
                this.createUser("superadmin@gmail.com", "1234", Role.SUPER_ADMIN);

                createLanguage("Francais");
                createLanguage("Arabe");
                createLanguage("Chinois");
                createLanguage("Japonais");
                createLanguage("Francais");
                createLanguage("Anglais");
                createLanguage("Espagnol");
                createLanguage("Portugais");
                createLanguage("Italien");
                createLanguage("Allemand");

                createSkill("Java");
                createSkill("Python");
                createSkill("C++");
                createSkill("C#");
                createSkill("Javascript");
                createSkill("Angular");
                createSkill("React");
                createSkill("Php");
                createSkill("VueJs");
                createSkill("Rust");
                createSkill("Ruby");

                createMentor(new MentorDTO(1L, "Marie", "Delaire", "Développeuse Front-End",
                        "Passionné par la création d'applications robustes et évolutives, je maîtrise Angular pour le front-end et Java pour le back-end. Mon expérience inclut le développement de systèmes complexes et la gestion de bases de données relationnelles.",
                        "http://localhost:8080/user/upload/marieD.webp", "git", "link", 1L));
                createMentor(new MentorDTO(2L, "Mathieu", "Chauveau", "Developpeur Web Java / Angular",
                        "Expert en React et Express, je crée des applications web modernes et performantes. Je suis également compétent en MongoDB pour le stockage des données et Node.js pour la logique serveur. ",
                        "http://localhost:8080/user/upload/mat.jpg", "githubJean", "linkedinJean", 2L));
                createMentor(new MentorDTO(3L, "Adam", "Hemamou", "Développeur Back-End",
                        "Avec une forte expertise en Angular et Express, je développe des applications web dynamiques et réactives. Mon objectif est de fournir des solutions complètes et efficaces en utilisant des bases de données NoSQL.",
                        "http://localhost:8080/user/upload/AdamC.jpg", "githubAlice", "linkedinAlice", 3L));
                createMentor(new MentorDTO(4L, "Raphaël", "Bard", "Développeur Front-End",
                        "Je combine React pour le développement front-end et Java pour les services back-end, garantissant des applications fluides et sécurisées. Mon approche intégrée me permet de gérer des projets complexes de bout en bout.",
                        "http://localhost:8080/user/upload/raph.jpeg", "githubLucas", "linkedinLucas", 4L));
                createMentor(new MentorDTO(5L, "Nelia", "Courtais", "Développeuse Back-End",
                        "Je suis spécialisé dans Angular pour le front-end et Node.js pour le back-end, créant ainsi des applications web réactives et performantes. Mon expérience inclut également la gestion de bases de données comme MongoDB.",
                        "http://localhost:8080/user/upload/fred.webp", "githubEmma", "linkedinEmma", 5L));
                createMentor(new MentorDTO(6L, "Pierre-Louis", "Bastin", "Développeur Full-Stack",
                        "Utilisant React pour le front-end et Spring Boot pour le back-end, je développe des applications web complètes et robustes. Je suis passionné par la création de systèmes évolutifs et sécurisés.",
                        "http://localhost:8080/user/upload/pilou.jpeg", "githubEmma", "linkedinEmma", 6L));
                createMentor(new MentorDTO(7L, "Aurore", "Valeix", "Développeuse Back-End",
                        "Avec Angular et Express, je construis des applications web interactives et modernes. Je m'assure que chaque projet est bien structuré et performant, en utilisant des bases de données SQL et NoSQL.",
                        "http://localhost:8080/user/upload/AuroreC.jpg", "githubEmma", "linkedinEmma", 7L));
                createMentor(new MentorDTO(8L, "Angeline", "Gauron", "Développeuse Back-End",
                        "En combinant React pour l'interface utilisateur et Java pour la logique serveur, je développe des applications efficaces et réactives. Je suis aussi compétent dans l'utilisation de bases de données relationnelles pour la gestion des données.",
                        "http://localhost:8080/user/upload/Angeline.jpeg", "githubEmma", "linkedinEmma", 8L));
                createMentor(new MentorDTO(9L, "Nassime", "Harmach", "Développeur Back-End",
                        "Expert en Angular pour le développement front-end et Node.js pour le back-end, je crée des applications web robustes et évolutives. Mon objectif est de fournir des solutions complètes et sécurisées.",
                        "http://localhost:8080/user/upload/NassimeC.jpg", "githubEmma", "linkedinEmma", 9L));
                createMentor(new MentorDTO(10L, "Nick", "Ellam", "Développeur Back-End",
                        "Expert en React pour le front-end et Express pour le back-end, je développe des applications web réactives et évolutives. J'ai une solide expérience en MongoDB et en API RESTful pour assurer une intégration fluide des données.",
                        "http://localhost:8080/user/upload/profilePic.jpg", "githubEmma", "linkedinEmma", 10L));
                createMentor(new MentorDTO(11L, "Jérémy", "Grégoire", "Développeur Back-End",
                        "En utilisant Angular pour le front-end et Express pour le back-end, je construis des applications web interactives et modernes. Je m'assure que chaque projet est bien structuré et performant, en utilisant des bases de données SQL et NoSQL.",
                        "http://localhost:8080/user/upload/jeremy.jpeg", "githubEmma", "linkedinEmma", 11L));
                createMentor(new MentorDTO(12L, "Yohan", "Harmach", "Développeur Back-End",
                        "Avec une forte expertise en React pour le front-end et Spring Boot pour le back-end, je développe des applications web complètes et performantes. Je suis passionné par la création de systèmes évolutifs et sécurisés.",
                        "http://localhost:8080/user/upload/yoyo.jpg", "githubEmma", "linkedinEmma", 12L));
                createMentor(new MentorDTO(13L, "Nicolas", "Leroux", "Développeur Back-End",
                        "Je suis spécialisé dans Angular pour le front-end et Node.js pour le back-end, créant des applications web réactives et robustes. Mon travail inclut également la gestion de bases de données comme MongoDB.",
                        "http://localhost:8080/user/upload/nico.jpeg", "githubEmma", "linkedinEmma", 13L));
                createMentor(new MentorDTO(14L, "Victor", "Garcia", "Développeur Back-End",
                        "Utilisant React pour l'interface utilisateur et Java pour le serveur, je crée des applications web performantes et sécurisées. Mon expertise inclut également l'intégration avec des bases de données relationnelles.",
                        "http://localhost:8080/user/upload/victor.jpg", "githubEmma", "linkedinEmma", 14L));
                createMentor(new MentorDTO(15L, "Sylvain", "Bonnaure", "Développeur Front-End",
                        "Passionné par Angular pour le front-end et Express pour le back-end, je construis des applications web interactives et modernes. Je suis également expérimenté dans la gestion de bases de données NoSQL.",
                        "http://localhost:8080/user/upload/sylvain.jpeg", "githubEmma", "linkedinEmma", 15L));

                List<Long> mentorids = new ArrayList<>();
                createStudent(new StudentDTO(1L, "Mahdi", "Mcheik", "Developpeur Junior", "J'ai envie d'apprendre !",
                        "http://localhost:8080/user/upload/mahdi.jpg",
                        "github/mariedelaire", "linkedin/mariedelaire", 16L, mentorids));
                createStudent(new StudentDTO(2L, "Jean", "Dupont", "Data Scientist", "Expert en données",
                        "https://picsum.photos/200", "github/jeandupont", "linkedin/jeandupont", 17L,
                        mentorids));
                createStudent(new StudentDTO(3L, "Alice", "Martin", "UX Designer", "Créatrice d'expériences",
                        "https://picsum.photos/200", "github/alicemartin", "linkedin/alicemartin", 18L,
                        mentorids));
                createStudent(new StudentDTO(4L, "Luc", "Girard", "Backend Developer", "Maître du backend",
                        "https://picsum.photos/200", "github/lucgirard", "linkedin/lucgirard", 19L, mentorids));
                createStudent(new StudentDTO(5L, "Sophie", "Leblanc", "Fullstack Developer", "Génie du Fullstack",
                        "https://picsum.photos/200", "github/sophieleblanc", "linkedin/sophieleblanc", 20L,
                        mentorids));

                adminService.createAdmin(21L,  AdminDTO.builder().firstname("Marie").lastname("Deladministateur")
                                .imgUrl("http://localhost:8080/user/upload/marieD.webp")
                        .build());
                superAdminService.createSuperAdmin(22L,  SuperAdminDTO.builder().firstname("Marie").lastname("Deladministateur")
                        .imgUrl("http://localhost:8080/user/upload/marieD.webp")
                        .build());

                // addExperiencesForUser1();
                // addExperiencesForUser2();

                // formation
                for (int i = 0; i < 15; i++) {
                        for (int j = 0; j < 3; j++) {
                                String title = "formation " + j;
                                FormationDTO formation = FormationDTO.builder()
                                        .title("Développeur web " + j)
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
                languages.add(new Language(2L, "Deutsch"));
                languages.add(new Language(3L, "Arabe"));

                for (long i = 1; i <= 10; i++) {
                        addUserLanguage(i, languages);
                }

                List<Skill> skills = new ArrayList<>();
                skills.add(new Skill(1L, "Java"));
                skills.add(new Skill(2L, "Python"));
                skills.add(new Skill(3L, "C++"));

                for (long i = 1; i <= 15; i++) {
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
                        .createdAt(LocalDateTime.now())
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

        private void createStudent(StudentDTO newStudent) throws Exception {
                studentService.addStudentByUserId(newStudent);
        }

        private void createFormation(FormationDTO formation) {
                userFormationService.addUserFormation(formation);
        }


        private void addUserLanguage(Long userId, List<Language> languages) {
                userLanguageService.updateUserLanguageList(userId, languages);
        }

        private void addUserSkill(Long userId, List<Skill> skills) {
                userSkillService.updateUserSkillList(userId, skills);
        }

}