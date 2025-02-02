package co.id.niluh.retail.management.config.prop;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Data
@ToString
@Configuration
@ConfigurationProperties(prefix = "info.application", ignoreUnknownFields = false)
public class ConfigProperties implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6940840201045361816L;
	private String version;
	private String name;
	private String profile;
	private String description;
}
