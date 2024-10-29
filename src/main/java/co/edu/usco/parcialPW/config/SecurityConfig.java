
package co.edu.usco.parcialPW.config;

import co.edu.usco.parcialPW.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> {

                    // Configurando endpoints públicos
                    request.requestMatchers("/loginPage", "/", "/swagger-ui/**", "/v3/api-docs/**", "/api/**").permitAll();
                    
                    // Configurando endpoints privados
                    request.requestMatchers("/vehicle/add",
                            "/vehicle/delete/**",
                            "/vehicle/modify/**",
                            "/vehicle/insert",
                            "/user/list").hasRole("ACOMODADOR");
                    request.requestMatchers("/vehicle/list").hasAnyRole("ACOMODADOR", "CLIENTE");

                    // Configurar el resto de endpoints NO ESPECIFICADOS
                    request.anyRequest().authenticated();
                })
                .formLogin(login -> {
                    login.loginPage("/loginPage");
                    login.successHandler(successHandler());
                    login.permitAll();
                })
                .logout(logout -> logout.permitAll())
                .exceptionHandling(ex -> ex.accessDeniedHandler(deniedHandler()));

        return http.build();
    }

    @Bean // Llama a los AuthenticationProviders, encargados de comunicarse y obtener los datos de la BBDD
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean // Llama a los Usuarios desde la base de datos
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean // Necesario en el provider para desencriptar contraseñas
    public PasswordEncoder passwordEncoder() {
        
        //Sin Encriptación
        return NoOpPasswordEncoder.getInstance();
        
        //Con Encriptación
//        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, auth) -> {
            response.sendRedirect("/");
        };
    }

    @Bean
    public AccessDeniedHandler deniedHandler() {
        return (request, response, auth) -> {
            response.sendRedirect("/403");
        };
    }

}
