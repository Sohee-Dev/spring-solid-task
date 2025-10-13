package com.puzzlix.solid_task._global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * AppConfig 전체 관련된 설정
 * WebConfig -> 웹 설정과 관련된 부분으로 만들 예정
 * */
@Configuration // IoC 대상
public class AppConfig {

    @Bean // IoC
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
