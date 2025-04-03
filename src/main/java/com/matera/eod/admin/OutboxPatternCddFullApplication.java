package com.matera.eod.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.matera.eod.admin",
		"com.matera.eod.admin.cdc"
})
@EnableJpaRepositories(basePackages = {
		"com.matera.eod.admin.broadcast.repository",
		"com.matera.eod.admin.cdc.repository",

})
@EntityScan(basePackages = {
		"com.matera.eod.admin.broadcast.model",
		"com.matera.eod.admin.mapper"
})
public class OutboxPatternCddFullApplication {

	public static void main(String[] args) {
		SpringApplication.run(OutboxPatternCddFullApplication.class, args);
	}

}
