package com.imcapp.imc_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImcAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImcAppApplication.class, args);
	}

}

// package com.imcapp.imc_app;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.autoconfigure.domain.EntityScan;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// @SpringBootApplication
// @EnableJpaRepositories(basePackages = "com.imcapp.imc_app.repository")
// @EntityScan(basePackages = "com.imcapp.imc_app.model")
// public class ImcAppApplication {
//   public static void main(String[] args) {
//     SpringApplication.run(ImcAppApplication.class, args);
//   }
// }
