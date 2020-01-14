package com.toptal.pushpendra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

import com.toptal.pushpendra.config.AppProperties;
import com.toptal.pushpendra.config.FileStorageProperties;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties({ AppProperties.class, FileStorageProperties.class })
public class TzStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(TzStoreApplication.class, args);
	}

}
