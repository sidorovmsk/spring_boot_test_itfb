package spring_boot_java.test_itfb.controllers;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring_boot_java.test_itfb.dto.PersonDto;
import spring_boot_java.test_itfb.exceptions.PersonNotFoundException;
import spring_boot_java.test_itfb.models.Person;
import spring_boot_java.test_itfb.repositories.PeopleRepository;
import spring_boot_java.test_itfb.services.AdminService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class AdminController {
    private final AdminService adminService;
    private final PeopleRepository peopleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(AdminService adminService, PeopleRepository peopleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.adminService = adminService;
        this.peopleRepository = peopleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public String getUsersView() {
        log.info("GET request to /users");
        adminService.doAdminStuff();
        return "users";
    }

    @ResponseBody
    @GetMapping("/api/users")
    public ResponseEntity<List<PersonDto>> getUsersJsonList() {
        log.info("GET request to /api/users");
        adminService.doAdminStuff();
        List<PersonDto> peopleDto = peopleRepository.findAll().stream()
                .map(user -> {
                    PersonDto personDto = modelMapper.map(user, PersonDto.class);
                    personDto.setRole(personDto.getRole().replace("ROLE_", ""));
                    return personDto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(peopleDto);
    }

    @ResponseBody
    @GetMapping("/user_json/{id}")
    public ResponseEntity<?> getUserJsonById(@PathVariable("id") int id) {
        log.info("GET request to /user_json/" + id);
        adminService.doAdminStuff();
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isEmpty()) {
            throw new PersonNotFoundException("Пользователь с идентификатором " + id + " не найден.");
        }
        PersonDto personDto = modelMapper.map(person, PersonDto.class);
        personDto.setRole(personDto.getRole().replace("ROLE_", ""));
        return ResponseEntity.ok(personDto);
    }

    @GetMapping("/user/{id}")
    public String getUserShowView(@PathVariable("id") int id) {
        log.info("GET request to /user/" + id);
        adminService.doAdminStuff();
        return "people/show";
    }

    @ResponseBody
    @DeleteMapping("/user_delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") int id) {
        log.info("DELETE request to /user_delete/" + id);
        adminService.doAdminStuff();
        System.out.println("deleting");
        if (peopleRepository.existsById(id)) {
            peopleRepository.deleteById(id);
            return ResponseEntity.ok("User with ID " + id + " has been deleted.");
        } else {
            throw new PersonNotFoundException("Пользователь с идентификатором " + id + " не найден.");
        }
    }

    @GetMapping("/user_edit/{id}")
    public String getUserEditView(@PathVariable("id") int id) {
        log.info("GET request to /user_edit/" + id);
        adminService.doAdminStuff();
        return "people/edit";
    }

    @ResponseBody
    @PostMapping("/user_edit/{id}")
    public ResponseEntity<?> editUserById(@PathVariable("id") int id,
                                          @RequestBody Person updatedPerson) {
        log.info("POST request to /user_edit/" + id);
        System.out.println("saving");
        adminService.doAdminStuff();
        Person person = peopleRepository.findById(id).orElse(null);
        if (person == null) {
            throw new PersonNotFoundException("Пользователь с идентификатором " + id + " не найден.");
        } else {
            person.setUsername(updatedPerson.getUsername());
            person.setRole(updatedPerson.getRole());
            if (!updatedPerson.getPassword().equals("")) {
                person.setPassword(passwordEncoder.encode(updatedPerson.getPassword()));
            }
            person.setEnabled(updatedPerson.isEnabled());
            peopleRepository.save(person);
            return ResponseEntity.ok("User with ID " + id + " has been deleted.");
        }
    }
}

