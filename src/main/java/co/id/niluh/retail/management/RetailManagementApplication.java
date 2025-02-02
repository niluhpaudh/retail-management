package co.id.niluh.retail.management;

import id.co.collega.encryption.anotation.EnableEncryptBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Configuration
@EnableJpaAuditing
@EnableAsync
@EnableEncryptBody
@PropertySources(value = {
		@PropertySource(value = "classpath:/static.properties", ignoreResourceNotFound = true)
})
public class RetailManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetailManagementApplication.class, args);
	}

}
