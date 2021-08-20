package com.ssafy.common;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(
        {FileUploadProperties.class}
)
@SpringBootApplication
public class CommonProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonProjectApplication.class, args);
	}
	@PostConstruct
	public void initApplication() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

}
