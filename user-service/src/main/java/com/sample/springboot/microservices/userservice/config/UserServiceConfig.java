package com.sample.springboot.microservices.userservice.config;

import com.sample.springboot.microservices.common.code.dto.UserReqDTO;
import com.sample.springboot.microservices.common.code.entity.Role;
import com.sample.springboot.microservices.common.code.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * User service config
 * @author Manjunath Asundi 
 */
@Component
public class UserServiceConfig {
	
    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}