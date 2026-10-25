package com.winwintek.web.backend.api.config.security.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityAppConfig {
    /*
    순환 참조 이슈로 인하여 추가 클레스로 생성 (생성자로 인한..)
    예시) A클레스   <--> B클레스
        *A,B 클레스가 서로가 의존함

    @Lazy 를 사용하여 해결 가능 > !!!하지만 애초에 순환참조 되지않도록 설계되야함
                             > 숨환참조가 되지않도록 설계를 위하여 따로 클레스 생성
     */
    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
}
