package com.study.clubproject.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // @Configuration: 해당 클래스를 설정 파일으로 빈에 등록한다.
@Log4j2
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { // WebSecurityConfigurerAdapter를 상속하여 보안 기능을 초기화 및 설정.
    @Bean                                                          // 현재는 deprecated 되었으므로, SecurityFilterChain으로 대체해야한다.
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // bcrypt 해시 함수를 사용하여 패스워드를 암호화.
    }
}
