package com.pcdhan.db.binlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.pcdhan.db.*"})
public class BinLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BinLogApplication.class, args);
	}

}
