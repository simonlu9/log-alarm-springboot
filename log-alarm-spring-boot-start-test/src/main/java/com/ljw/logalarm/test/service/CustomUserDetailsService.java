package com.ljw.logalarm.test.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 假设用户名是 "admin" 密码是 "password"
        if ("admin".equals(username)) {
            return User.builder()
                    .username("admin")
                    .password("{noop}password") // 使用 {noop} 表示不加密密码
                    .roles("USER") // 分配用户角色
                    .build();
        }
        throw new UsernameNotFoundException("User not found");
    }
}
