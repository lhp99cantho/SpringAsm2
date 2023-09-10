package com.example.asg2.Config;

import com.example.asg2.Security.MemberDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;


@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private MemberDetailsService memberDetailsService;

    public static final String[] ENDPOINTS_WHITELIST = {
            "/login",
            "/logout",
            "/",
            "/access-denied",
            "/register",
    };
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";
    public static final String LOGIN_FAIL_URL = LOGIN_URL + "?error";
    public static final String DEFAULT_SUCCESS_URL = "/profile";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(request ->
                                request.requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                                        .anyRequest().authenticated())
                .exceptionHandling(customizer -> customizer.accessDeniedHandler(accessDeniedHandler()))
                .formLogin(form -> form
                        .loginPage(LOGIN_URL) // Xác định trang đăng nhập của ứng dụng
                        .loginProcessingUrl(LOGIN_URL) // URL để xử lý quá trình đăng nhập
                        .failureUrl(LOGIN_FAIL_URL) // URL để chuyển hướng sau khi đăng nhập thất bại
                        .usernameParameter(USERNAME) //  Xác định tên của các trường USERNAME trong biểu mẫu HTML
                        .passwordParameter(PASSWORD) // Xác định tên của các trường PASSWORD trong biểu mẫu HTML
                        .defaultSuccessUrl(DEFAULT_SUCCESS_URL)) // URL mặc định sau khi đăng nhập thành công
                .logout(logout -> logout
                        .logoutUrl(LOGOUT_URL) // URL để xử lý quá trình đăng xuất
                        .logoutSuccessUrl(LOGIN_URL + "?logout") // URL mặc định sau khi đăng xuất thành công
                        .invalidateHttpSession(true) // Hủy bỏ phiên làm việc của người dùng sau khi đăng xuất
                        .clearAuthentication(true) // Xóa thông tin xác thực của người dùng sau khi đăng xuất
                        .deleteCookies("JSESSIONID")) // Xóa cookie JSESSIONID sau khi đăng xuất
                .sessionManagement(session -> session // Cấu hình quản lý phiên làm việc
                        .maximumSessions(1) // giới hạn số phiên đăng nhập đồng thời của một người dùng
                        .maxSessionsPreventsLogin(true))
                .cors(cors -> {
                            cors.disable();
                }).csrf(csrf -> {
                    csrf.disable();
                });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(memberDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        AccessDeniedHandlerImpl handler = new AccessDeniedHandlerImpl();
        handler.setErrorPage("/access-denied");
        return handler;
    }

}
