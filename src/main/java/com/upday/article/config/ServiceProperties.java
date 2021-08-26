package com.upday.article.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "service.props")
@Component
@Data
public class ServiceProperties {
	
	private  String postNotFound;
	private  String authorNotFound;
	private  String keyWordNotFound;
	private String noArticlesFound;
	
}
