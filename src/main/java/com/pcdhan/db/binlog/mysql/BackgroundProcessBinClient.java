package com.pcdhan.db.binlog.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.github.shyiko.mysql.binlog.BinaryLogClient;

@Component
public class BackgroundProcessBinClient implements CommandLineRunner {

	@Autowired
	BinaryLogClient client;

	@Override
	@Async("asyncExecutor")
	public void run(String... args) throws Exception {

		client.connect();
	}

}
