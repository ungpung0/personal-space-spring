package com.study.clubproject.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

    // 'security>dto>service>ClubUserDetailsService'가 자동으로 UserDetailsService로 인식되어 아래 메소드는 사용을 중지한다.
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // AuthenticationManagerBuilder는 코드로 인증 매니저를 설정.
        auth.inMemoryAuthentication().withUser("user") // inMemoryAuthentication()는 코드로 로그인을 확인할 때 사용한다.
                .password("password")
                .roles("USER");
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/sample/all").permitAll()         // 'sample/all'를 모든 사용자에게 허락한다.
                .antMatchers("/sample/member").hasRole("USER"); // 'sample/member'를 'USER'에게 허락한다.

        http.formLogin();      // 인증에 문제가 발생할 시 로그인 화면으로 이동.
        http.csrf().disable(); // CSRF 토큰 발행을 금지.
        http.logout();         // 로그아웃 처리.
    }
}
