package cn.leadeon.sprmail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SprMailApplication {

	public static void main(String[] args) {
		SpringApplication.run(SprMailApplication.class, args);
	}

}
