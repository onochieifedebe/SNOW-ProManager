package com.enatcpromanager.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("package com.enatcpromanager.com.dao")
public class EnatcProManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnatcProManagerApplication.class, args);
	}

}
