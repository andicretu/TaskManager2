package taskmanager.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import taskmanager.application.Models.UserModel;
import taskmanager.application.Repositories.UserRepository;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final UserRepository userRepository;
    @Autowired
    public WebSecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/registerUser").permitAll()
                        .requestMatchers("/taskManager/show-task/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .successHandler((request, response, authentication) -> {
                            String email = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
                            response.sendRedirect("/taskManager/showUser/" + email);
                        })
                )
                .logout((logout) -> logout.permitAll());
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            UserModel userModel = userRepository.findByEmail(email);
            if (userModel == null) {
                throw new UsernameNotFoundException("User not found with email: " + email);
            }
            return new org.springframework.security.core.userdetails.User(
                    userModel.getEmail(),
                    userModel.getPassword(),
                    Collections.emptyList()
            );
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }
}
