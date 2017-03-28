package com.wise.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * 程序入口
 */
@SpringBootApplication
@ComponentScan("com.wise")
@ImportResource(value = { "classpath:orm-config.xml", "classpath:dubbo-provider.xml" })
public class ProviderApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(ProviderApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProviderApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		try {
			Thread.currentThread().join();
		} catch (Exception e) {
			LOG.error("startup error!", e);
		}
	}
}
