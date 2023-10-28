package spring_boot_java.test_itfb.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring_boot_java.test_itfb.dto.PersonDto;
import spring_boot_java.test_itfb.models.Person;
import spring_boot_java.test_itfb.services.AdminService;

import java.util.List;

@Slf4j
@Controller
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
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
        return adminService.getUsersJsonList();
    }

    @ResponseBody
    @GetMapping("/user_json/{id}")
    public ResponseEntity<?> getUserJsonById(@PathVariable("id") int id) {
        log.info("GET request to /user_json/" + id);
        adminService.doAdminStuff();
        return adminService.getUserJsonById(id);
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
        return adminService.deleteUserById(id);
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
        adminService.doAdminStuff();
        return adminService.editUserById(id, updatedPerson);
    }
}

