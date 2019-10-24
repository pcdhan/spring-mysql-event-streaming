package com.pcdhan.db.binlog.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;

@Configuration
public class MySqlBinLogClient {

	private final Logger logger = LoggerFactory.getLogger(MySqlBinLogClient.class);

	@Value("${host}")
	private String host;

	@Bean
	public BinaryLogClient binaryLogClient() {
		BinaryLogClient client = new BinaryLogClient(host, 3306, "root", "password");
		EventDeserializer eventDeserializer = new EventDeserializer();
		eventDeserializer.setCompatibilityMode(EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
				EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY);
		client.setEventDeserializer(eventDeserializer);
		client.setBinlogPosition(731L);
		client.registerEventListener(new BinaryLogClient.EventListener() {

			@Override
			public void onEvent(Event event) {
				logger.info(event.toString());
			}

		});

		return client;
	}
}
