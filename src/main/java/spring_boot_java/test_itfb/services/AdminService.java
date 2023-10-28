package spring_boot_java.test_itfb.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring_boot_java.test_itfb.dto.PersonDto;
import spring_boot_java.test_itfb.exceptions.PersonNotFoundException;
import spring_boot_java.test_itfb.models.Person;
import spring_boot_java.test_itfb.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final PeopleRepository peopleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(PeopleRepository peopleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SOME_OTHER')")
    public void doAdminStuff() {
        System.out.println("Only admin here");
    }

    public ResponseEntity<List<PersonDto>> getUsersJsonList() {
        List<PersonDto> peopleDto = peopleRepository.findAll().stream()
                .map(user -> {
                    PersonDto personDto = modelMapper.map(user, PersonDto.class);
                    personDto.setRole(personDto.getRole().replace("ROLE_", ""));
                    return personDto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(peopleDto);
    }

    public ResponseEntity<?> getUserJsonById(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isEmpty()) {
            throw new PersonNotFoundException("Пользователь с идентификатором " + id + " не найден.");
        }
        PersonDto personDto = modelMapper.map(person, PersonDto.class);
        personDto.setRole(personDto.getRole().replace("ROLE_", ""));
        return ResponseEntity.ok(personDto);
    }

    public ResponseEntity<?> deleteUserById(int id) {
        if (peopleRepository.existsById(id)) {
            peopleRepository.deleteById(id);
            return ResponseEntity.ok("User with ID " + id + " has been deleted.");
        } else {
            throw new PersonNotFoundException("Пользователь с идентификатором " + id + " не найден.");
        }
    }

    public ResponseEntity<?> editUserById(int id, Person updatedPerson) {
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
