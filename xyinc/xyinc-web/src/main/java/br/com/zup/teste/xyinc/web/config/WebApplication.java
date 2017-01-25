package br.com.zup.teste.xyinc.web.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * WebApplication
 * Spring Configuration for business subproject.
 * Also starts web container (Tomcat) through Spring Boot.
 *
 * @author lucasottoni
 *
 */
@SpringBootApplication
@EnableWebMvc
@Configuration
@Import(value = {br.com.zup.teste.xyinc.business.config.ContextConfig.class})
@ComponentScan("br.com.zup.teste.xyinc.web")
public class WebApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}
