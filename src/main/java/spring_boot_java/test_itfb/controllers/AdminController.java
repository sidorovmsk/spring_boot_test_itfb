package spring_boot_java.test_itfb.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @GetMapping("/view/users")
    public String getUsersView() {
        log.info("GET request to /view/users");
        adminService.doAdminStuff();
        return "users";
    }

    @ResponseBody
    @GetMapping("/users/list")
    public ResponseEntity<List<PersonDto>> getUsersJsonList() {
        log.info("GET request to /users/list");
        adminService.doAdminStuff();
        return adminService.getUsersJsonList();
    }

    @ResponseBody
    @GetMapping("/users/{id}")
    public ResponseEntity<PersonDto> getUserJsonById(@PathVariable("id") int id) {
        log.info("GET request to /users/" + id);
        adminService.doAdminStuff();
        return adminService.getUserJsonById(id);
    }

    @GetMapping("/view/users/{id}")
    public String getUserShowView(@PathVariable("id") int id) {
        log.info("GET request to /view/users/" + id);
        adminService.doAdminStuff();
        return "people/show";
    }

    @ResponseBody
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") int id) {
        log.info("DELETE request to /users/" + id);
        adminService.doAdminStuff();
        return adminService.deleteUserById(id);
    }

    @GetMapping("/view/users/edit/{id}")
    public String getUserEditView(@PathVariable("id") int id) {
        log.info("GET request to /view/users/edit/" + id);
        adminService.doAdminStuff();
        return "people/edit";
    }

    @ResponseBody
    @PutMapping("/users/{id}")
    public ResponseEntity<String> editUserById(@PathVariable("id") int id,
                                          @RequestBody Person updatedPerson) {
        log.info("PUT request to /users/" + id);
        adminService.doAdminStuff();
        return adminService.editUserById(id, updatedPerson);
    }
}

