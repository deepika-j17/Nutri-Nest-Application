package com.example.Nutri_Nest.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity//no need of default config i myself will do it
public class SecurityConfiguration {

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //we need to do some filters in it
        //1. Disable the CSRF
             // i)Same site strict
             // ii) Session -> STATELESS
       // 2. Fundamental interface and Lambda expression


        //disabling the csrf
        return httpSecurity.csrf(customizer ->customizer.disable())
        //making each requests to be authenticated
               .authorizeHttpRequests(request ->request
                       .requestMatchers("/admin/**").hasRole("ADMIN") //ROLE->ADMIN
                       .requestMatchers("/users/**").permitAll()//ROLE->USER
                       .requestMatchers("/view/**").permitAll()
                       .requestMatchers("/public/**").permitAll() //PUBLIC
                       .anyRequest().authenticated())
//                .formLogin(Customizer.withDefaults())
                //generate the login form(only for browsers)
       // httpSecurity.formLogin(Customizer.withDefaults());
        //if for any other clients(eg:postman)
                .httpBasic(Customizer.withDefaults())
        //Creating different SessionId for each Requests
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();

    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        //since it is works with database
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); //no pwd //not at all doing any kind of encoding
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService); //directly cant give userDetailsService since it is an interface
        return provider;

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
