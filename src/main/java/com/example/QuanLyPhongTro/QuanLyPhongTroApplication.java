package com.example.QuanLyPhongTro;

import com.example.QuanLyPhongTro.models.Users;
import com.example.QuanLyPhongTro.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class QuanLyPhongTroApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(QuanLyPhongTroApplication.class, args);
	}

	@Autowired
	UsersRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
//		Users user = new Users();
//		user.setUsername("LeTuanLmao");
//		user.setPassword(passwordEncoder.encode("123"));
//		userRepository.save(user);
//		System.out.println(user);
	}
}
