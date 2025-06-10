package br.ufrn.tads.web_car_store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Permite acesso público a estas URLs
                        .requestMatchers("/", "/index", "/login", "/cadusuario", "/salvarusuario").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/fonts/**").permitAll() // Permite acesso aos arquivos estáticos

                        // Protege as rotas de ADMIN
                        .requestMatchers("/admin/**", "/cadastro", "/salvar", "/editar/**", "/deletar/**", "/restaurar/**").hasRole("ADMIN")

                        // Protege as rotas de USER
                        .requestMatchers("/verCarrinho", "/adicionarCarrinho/**", "/finalizarCompra").hasRole("USER")

                        // Qualquer outra requisição precisa de autenticação
                        .anyRequest().authenticated()
                )
                // Configura o formulário de login
                .formLogin(form -> form
                        .loginPage("/login") // Nossa página de login customizada
                        .defaultSuccessUrl("/index", true) // Redireciona para /index após login com sucesso
                        .permitAll()
                )
                // Configura o logout
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout") // Redireciona para /login com um parâmetro 'logout'
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Define o BCrypt como o codificador de senhas
        return new BCryptPasswordEncoder();
    }
}