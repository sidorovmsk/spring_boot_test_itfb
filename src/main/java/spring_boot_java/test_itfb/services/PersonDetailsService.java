package spring_boot_java.test_itfb.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring_boot_java.test_itfb.models.Person;
import spring_boot_java.test_itfb.repositories.PeopleRepository;
import spring_boot_java.test_itfb.security.PersonDetails;

import java.util.Optional;

@Slf4j
@Service
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(s);

        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new PersonDetails(person.get());
    }

    public boolean userExists(String username) {
        try {
            loadUserByUsername(username);
            log.info("Пользователь \"" + username + "\" уже существует");
            return true;
        } catch (UsernameNotFoundException ignored) {
            log.info("Пользователь \"" + username + "\" НЕ существует, будет создан");
            return false;
        }
    }
}
