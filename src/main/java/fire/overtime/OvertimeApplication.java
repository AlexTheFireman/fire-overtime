package fire.overtime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication

@EnableJpaRepositories
public class OvertimeApplication {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("https://overtime-front.herokuapp.com");
//                        .allowedOrigins("http://localhost:3000");
			}
		};
	}
	public static void main(String[] args) { SpringApplication.run(OvertimeApplication.class, args); }
}


