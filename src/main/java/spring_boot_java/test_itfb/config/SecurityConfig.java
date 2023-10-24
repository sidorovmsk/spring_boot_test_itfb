package spring_boot_java.test_itfb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import spring_boot_java.test_itfb.services.PersonDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // конфигурируем авторизацию //todo изучить вопрос и включить csrf (проблема отправки токена с фронта)
        http.csrf().ignoringRequestMatchers(new AntPathRequestMatcher("/user_delete/**"))
                .and()
                .csrf().ignoringRequestMatchers(new AntPathRequestMatcher("/user_edit/**"))
                .and()
                .csrf().ignoringRequestMatchers(new AntPathRequestMatcher("/book_edit/**"))
                .and()
                .authorizeRequests()
//                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/user_delete/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/book_edit/**").hasRole("ADMIN")
                .antMatchers("/login", "/registration", "/error", "/about").permitAll()
                .anyRequest().hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin().loginPage("/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/logout", true)
                .failureUrl("/login?error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");
    }

    // Настраиваем аутентификацию
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
