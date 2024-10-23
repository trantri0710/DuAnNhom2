package com.fsa.cursus;

import com.fsa.cursus.model.entity.Account;
import com.fsa.cursus.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CursusApplication implements CommandLineRunner{
	@Autowired
	private AccountService accountService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		String password = "123456";

		Account admin = accountService.findByUsername("admin");
		if (admin == null) {
			admin = new Account();
			admin.setUsername("admin");
			admin.setFullName("Admin");
			admin.setPassword(passwordEncoder.encode(password));
			admin.setRole("ADMIN");
			admin.setStatus(true);

			accountService.saveOrUpdate(admin);
		}

		Account instructor = accountService.findByUsername("instructor");
		if (instructor == null) {
			instructor = new Account();
			instructor.setUsername("instructor");
			instructor.setFullName("Instructor");
			instructor.setPassword(passwordEncoder.encode(password));
			instructor.setRole("INSTRUCTOR");
			instructor.setStatus(true);

			accountService.saveOrUpdate(instructor);
		}

		Account student = accountService.findByUsername("instructor");
		if (student == null) {
			student = new Account();
			student.setUsername("student");
			student.setFullName("Student");
			student.setPassword(passwordEncoder.encode(password));
			student.setRole("STUDENT");
			student.setStatus(true);

			accountService.saveOrUpdate(student);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(CursusApplication.class, args);
	}
}
