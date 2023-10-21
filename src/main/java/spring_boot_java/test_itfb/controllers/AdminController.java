package spring_boot_java.test_itfb.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import spring_boot_java.test_itfb.dto.PersonDto;
import spring_boot_java.test_itfb.models.Book;
import spring_boot_java.test_itfb.models.Person;
import spring_boot_java.test_itfb.repositories.PeopleRepository;
import spring_boot_java.test_itfb.services.AdminService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AdminController {
    private final AdminService adminService;
    private final PeopleRepository peopleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminController(AdminService adminService, PeopleRepository peopleRepository, ModelMapper modelMapper) {
        this.adminService = adminService;
        this.peopleRepository = peopleRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/admin")
    public String adminPage() {
        adminService.doAdminStuff();
        return "admin";
    }

    @ResponseBody
    @GetMapping("/users")
    public ResponseEntity<List<PersonDto>> getAllUsers() {
        adminService.doAdminStuff();
        List<PersonDto> peopleDto = peopleRepository.findAll().stream()
                .map(user -> modelMapper.map(user, PersonDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(peopleDto);
    }

    @ResponseBody
    @GetMapping("/user/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id) {
        adminService.doAdminStuff();
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
        }
        return ResponseEntity.ok(modelMapper.map(person, PersonDto.class));
    }
}
