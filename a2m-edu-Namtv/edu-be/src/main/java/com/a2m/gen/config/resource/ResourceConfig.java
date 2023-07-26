package com.a2m.gen.config.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan
public class ResourceConfig implements WebMvcConfigurer{
	
	@Value("${path.upload.dir}")
	private String uploadDir;
	@Value("${path.upload.prefix}")
	private String uploadPrefix;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler( uploadPrefix + "/**")
	    .addResourceLocations("file:" + uploadDir + "/");
	}
}
