package spring_boot_java.test_itfb.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import spring_boot_java.test_itfb.components.PersonValidator;
import spring_boot_java.test_itfb.dto.PersonDto;
import spring_boot_java.test_itfb.models.Person;
import spring_boot_java.test_itfb.services.AdminService;
import spring_boot_java.test_itfb.services.RegistrationService;

import javax.validation.Valid;

@Controller
@Slf4j
public class AuthController {

    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    private final AdminService adminService;

    @Autowired
    public AuthController(RegistrationService registrationService, PersonValidator personValidator, AdminService adminService) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
        this.adminService = adminService;
    }

    @GetMapping("/login")
    public String getLoginView() {
        log.info("GET request to /login");
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationView(@ModelAttribute("person") Person person) {
        adminService.doAdminStuff();
        log.info("GET request to /registration");
        return "registration";
    }

    @PostMapping("/registration")
    public String createNewPerson(@ModelAttribute("person") @Valid Person person,
                                  BindingResult bindingResult) {
        adminService.doAdminStuff();
        log.info("POST request to /registration with = " + person.getUsername());
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "registration";

        registrationService.register(person);

        return "redirect:/login";
    }


    @GetMapping("/view/about")
    public String getAboutView() {
        log.info("GET request to /view/about");
        return "about";
    }

    @ResponseBody
    @GetMapping("/about")
    public ResponseEntity<?> showUserInfo() {
        log.info("GET request to /about");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            PersonDto userInfo = new PersonDto();
            userInfo.setUsername(userDetails.getUsername());
            userInfo.setRole(userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_",""));
            return ResponseEntity.ok(userInfo);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Пользователь не аутентифицирован");
    }

    @GetMapping("/logout")
    public String getLogoutView() {
        log.info("GET request to /logout");
        return "logout";
    }
}
