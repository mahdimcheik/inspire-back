package com.poec.projet_backend.util;

import com.poec.projet_backend.domains.language.Language;
import com.poec.projet_backend.domains.language.LanguageRepository;
import com.poec.projet_backend.domains.mentor.Mentor;
import com.poec.projet_backend.domains.mentor.MentorDTO;
import com.poec.projet_backend.domains.mentor.MentorRepository;
import com.poec.projet_backend.domains.mentor.MentorService;
import com.poec.projet_backend.domains.skill.Skill;
import com.poec.projet_backend.domains.skill.SkillRepository;
import com.poec.projet_backend.domains.student.Student;
import com.poec.projet_backend.domains.student.StudentRepository;
import com.poec.projet_backend.user_app.Role;
import com.poec.projet_backend.user_app.UserApp;
import com.poec.projet_backend.user_app.UserAppRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final UserAppRepository userAppRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final LanguageRepository languageRepository;
    private final SkillRepository skillRepository;
    private final MentorService mentorService;
    private final MentorRepository mentorRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        //if(this.userAppRepository.findByEmail("admin@admin.com").isEmpty()) {
            this.createUser("mentor1@gmail.com", "1234", Role.mentor);
            this.createUser("mentor2@gmail.com", "1234", Role.mentor);
            this.createUser("mentor3@gmail.com", "1234", Role.mentor);
            this.createUser("mentor4@gmail.com", "1234", Role.mentor);
            this.createUser("mentor5@gmail.com", "1234", Role.mentor);

            this.createUser("student1@gmail.com", "1234", Role.student);
            this.createUser("student2@gmail.com", "1234", Role.student);
            this.createUser("student3@gmail.com", "1234", Role.student);
            this.createUser("student4@gmail.com", "1234", Role.student);
            this.createUser("student5@gmail.com", "1234", Role.student);



       // }
        createLanguage("francais");
        createLanguage("deutsch");
        createLanguage("arabe");
        createLanguage("chinois");
        createLanguage("japonais");

        createSkill("Java");
        createSkill("Python");
        createSkill("C++");
        createSkill("C#");
        createSkill("Javascript");


        createMentor(new MentorDTO("Marie", "Delo", "super dev", "Super mentorette", "no fking image","git","link",1L));

        createMentor(new MentorDTO("Mathieu", "Dupont", "Data Scientist", "Expert en Data", "https://picsum.photos/200", "githubJean", "linkedinJean", 2L));
        createMentor(new MentorDTO("Mahdi", "Martin", "UX Designer", "Créateur d'Expérience", "https://picsum.photos/200", "githubAlice", "linkedinAlice", 3L));
        createMentor(new MentorDTO("Lucas", "Moreau", "DevOps", "Spécialiste en Infrastructure", "https://picsum.photos/200", "githubLucas", "linkedinLucas", 4L));
        createMentor(new MentorDTO("Emma", "Leroy", "Product Manager", "Gestionnaire de Produit", "https://picsum.photos/200", "githubEmma", "linkedinEmma", 5L));


       // createMentor(new MentorDTO("Marie", "Delaire", "Dev", "Super Mentorette", "https://picsum.photos/200", "github", "linkedin", 1L));

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

    private void createUser(String email, String password, Role role) {
        UserApp user1 = UserApp.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(role.name())
                .build();

        this.userAppRepository.save(user1);
    }

    private void createLanguage(String newName){
        Language language = new Language();
        language.setName(newName);
        languageRepository.save(language);
    }

    private void createSkill(String newName){
        Skill skill = new Skill();
        skill.setName(newName);
        skillRepository.save(skill);
    }

    private void createMentor(MentorDTO newMentor){
         mentorService.addMentorByUserId(newMentor);
    }

   /* private void createStudent(String email, String password, String firstname, String lastname, String title, String description, String imgUrl, String linkedinUrl, String githubUrl) {
        UserApp userApp = createUser(email, password, Role.student);
        Student student1 = Student.builder()
                .firstname(firstname)
                .lastname(lastname)
                .title(title)
                .description(description)
                .imgUrl(imgUrl)
                .linkedinUrl(linkedinUrl)
                .githubUrl(githubUrl)
                .user(userApp)
                .build();

        this.studentRepository.save(student1);
    } */
}
