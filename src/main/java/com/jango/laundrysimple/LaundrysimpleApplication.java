package com.jango.laundrysimple;

import com.jango.laundrysimple.model.Roles;
import com.jango.laundrysimple.repository.RolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@Slf4j
public class LaundrysimpleApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaundrysimpleApplication.class, args);
	}

	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CommandLineRunner demoData(RolesRepository rolesRepo) {
		return args -> {


			if (rolesRepo.findAll().isEmpty()){
				Roles roles = new Roles(1, "ADMIN");
				Roles mRoles = rolesRepo.save(roles);
				log.info("Role Id:::::{}Role Name::::::{}",mRoles.getId(),mRoles.getName());
				Roles roles1 = new Roles(2, "USER");
				Roles mRoles1 =rolesRepo.save(roles1);
				log.info("Role Id:::::{}Role Name::::::{}",mRoles1.getId(),mRoles1.getName());


			}

		};
	}

}
