package com.example.greenmarket.config;

import com.example.greenmarket.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.concurrent.TimeUnit;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize

                        .requestMatchers("/login","/", "/registro","/admin/registro","/anuncios/ver/**" ,"/css/**", "/js/**", "/imagesAnuncio/**","/images/**" , "/fonts/**").permitAll() // Permitir acceso sin logueo
                        // Rutas que requieren roles específicos
                        .requestMatchers("/anuncios/**").permitAll()
                        .requestMatchers("/anuncios/nuevo/**").permitAll()
                        .requestMatchers("/anuncios/editar/**").permitAll()
                        .requestMatchers("/anuncios/borrar/**").permitAll()
                        .requestMatchers("/categoria/nuevo").hasRole("ADMIN")
                        .requestMatchers("/anuncios/panel/inicio").authenticated()

                )


                .httpBasic(Customizer.withDefaults())
                .formLogin(
                        form -> form
                                .loginPage("/")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/anuncios/panel/inicio", true)
                                .failureUrl("/login?error=true")
                                .permitAll()
                )

                .rememberMe(rememberMe ->
                        rememberMe
                                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(5))
                                .key("remember-me-key")
                )


                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );





        return http.build();
    }

    /*
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("nacho")
                .password("1234")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }
    */

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(customUserDetailsService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
