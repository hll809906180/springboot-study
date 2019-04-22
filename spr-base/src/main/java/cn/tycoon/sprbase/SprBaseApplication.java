package cn.tycoon.sprbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan("cn.tycoon.sprbase.comm")
@SpringBootApplication
public class SprBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SprBaseApplication.class, args);
	}

}
