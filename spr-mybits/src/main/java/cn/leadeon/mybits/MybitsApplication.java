package cn.leadeon.mybits;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@Configuration
@MapperScan(basePackages="cn.leadeon.mybits.mapper")
public class MybitsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybitsApplication.class, args);
	}

}
