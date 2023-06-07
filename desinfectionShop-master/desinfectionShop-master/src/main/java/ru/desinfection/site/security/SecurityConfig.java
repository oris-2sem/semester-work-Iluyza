package ru.desinfection.site.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.desinfection.site.security.filter.JwtAuthenticationFilter;

@Configuration
@CrossOrigin(origins = "http://92.63.101.66", allowCredentials = "true")
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/auth/**", "/api/v1/auth/**", "/api/**", "/addItem", "/", "/catalog/**", "/item/**", "/cart", "/checkout").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.logout(logout -> {
            logout.
                    deleteCookies("Authorization")
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/auth/login")
                    .invalidateHttpSession(true);
                        });

        return http.build();
    }

    @Bean
    @Order(0)
    SecurityFilterChain resources(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .requestMatchers((matchers) -> matchers.antMatchers("/static/**", "/js/**", "/css/**", "/images/**", "/img/**", "/scss/**", "/fonts/**"))
                .authorizeRequests((authorize) -> authorize.anyRequest().permitAll())
                .requestCache().disable()
                .securityContext().disable()
                .sessionManagement().disable();

        return http.build();
    }
}
