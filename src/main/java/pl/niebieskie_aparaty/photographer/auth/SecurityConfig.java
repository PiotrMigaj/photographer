package pl.niebieskie_aparaty.photographer.auth;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}password") // {noop} tells Spring Security to use plain text password
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/about", "/portfolio", "/contact", "/login", "/css/**").permitAll()
                        .requestMatchers("/addPhoto").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/addPhoto", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/").deleteCookies("JSESSIONID")
                        .permitAll()
                );
        http.csrf().disable();

        http
                // Other configurations
                .sessionManagement(session -> session
                        .invalidSessionUrl("/login") // Redirect to login page for an invalid session
                        .maximumSessions(1) // Maximum number of sessions per user
                        .maxSessionsPreventsLogin(false) // Allow multiple logins from the same user
                        .expiredUrl("/login?expired"));
        return http.build();
    }
}

