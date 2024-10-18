//package com.AppRH.AppRH;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//
//@Configuration
//public class WebConfig {
//
//    // Configura os usuários em memória
//    @SuppressWarnings("deprecation")
//	@Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("andre")
//                .password("andre")
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("adm")
//                .password("adm")
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }
//
//    // Configura as regras de segurança (acesso às URLs e autenticação)
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests(authorizeRequests -> 
//                authorizeRequests
//                    .requestMatchers("/", "/vagas**", "/home**").permitAll() // Substitui antMatchers por requestMatchers
//                    .anyRequest().authenticated()
//            )
//            .formLogin(form -> 
//                form
//                    .permitAll()
//            )
//            .logout(logout -> 
//                logout
//                    .permitAll()
//            )
//            .csrf(csrf -> csrf.disable());  // Se necessário desativar o CSRF
//        
//        return http.build();
//    }
//}
