package spring_boot_java.test_itfb.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring_boot_java.test_itfb.dto.PersonDto;
import spring_boot_java.test_itfb.models.Person;
import spring_boot_java.test_itfb.repositories.PeopleRepository;
import spring_boot_java.test_itfb.services.AdminService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//todo разнести логически контроллеры и выделить сервисный слой для некоторых действий
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

    @GetMapping("/admin")
    public String adminPage() {
        adminService.doAdminStuff();
        return "admin";
    }

    @GetMapping("/users")
    public String getHTMLUsers() {
        adminService.doAdminStuff();
        return "users";
    }

    @ResponseBody
    @GetMapping("/api/users")
    public ResponseEntity<List<PersonDto>> getAllUsers() {
        adminService.doAdminStuff();
        List<PersonDto> peopleDto = peopleRepository.findAll().stream()
                .map(user -> modelMapper.map(user, PersonDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(peopleDto);
    }

    @ResponseBody
    @GetMapping("/user_json/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id) {
        adminService.doAdminStuff();
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
        }
        return ResponseEntity.ok(modelMapper.map(person, PersonDto.class));
    }

    @GetMapping("/user/{id}")
    public String show(@PathVariable("id") int id, Model model) {
//        model.addAttribute("person", peopleRepository.findById(id));
        adminService.doAdminStuff();
        return "people/show";
    }

    @ResponseBody
    @DeleteMapping("/user_delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") int id) {
        adminService.doAdminStuff();
        System.out.println("deleting");
        if (peopleRepository.existsById(id)) {
            peopleRepository.deleteById(id);
            return ResponseEntity.ok("User with ID " + id + " has been deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
        }
    }

    @GetMapping("/user_edit/{id}")
    public String showUserById(@PathVariable("id") int id) {
        adminService.doAdminStuff();
        return "people/edit";
    }

    @ResponseBody
    @PostMapping("/user_edit/{id}")
    public ResponseEntity<?> editUserById(@PathVariable("id") int id,
                                          @RequestBody Person updatedPerson) {
        System.out.println("saving");
        adminService.doAdminStuff();
        Person person = peopleRepository.findById(id).orElse(null);
        if (person == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
        } else {
            person.setUsername(updatedPerson.getUsername());
            person.setRole(updatedPerson.getRole());
            if (!updatedPerson.getPassword().equals("")){
                person.setPassword(passwordEncoder.encode(updatedPerson.getPassword()));
            }
            person.setEnabled(updatedPerson.isEnabled());
            peopleRepository.save(person);
            return ResponseEntity.ok("User with ID " + id + " has been deleted.");
        }
    }
}

