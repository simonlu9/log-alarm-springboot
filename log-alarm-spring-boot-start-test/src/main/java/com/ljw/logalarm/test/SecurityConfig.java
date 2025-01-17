package com.ljw.logalarm.test;

import com.ljw.logalarm.test.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

//@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests()
            .regexMatchers(new String[]{"/login", "/error"}).permitAll() // 允许访问登录页面
            .anyRequest().authenticated() // 其他请求需要认证
            .and()
            .formLogin()
            .loginPage("/login") // 自定义登录页面
            .defaultSuccessUrl("/home", true) // 登录成功后跳转到 /home
            .permitAll()
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login") // 退出后跳转到登录页面
            .permitAll();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 密码不加密（仅用于演示，生产环境请使用加密）
        return NoOpPasswordEncoder.getInstance();
    }
}
