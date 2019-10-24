package com.pcdhan.db.binlog;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.shyiko.mysql.binlog.BinaryLogFileReader;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.pcdhan.db.binlog.mysql.Machine;

@RestController
public class MysqlBinLogController {
	private final Logger logger = LoggerFactory.getLogger(MysqlBinLogController.class);

	@Value("${host}")
	private String host;

	@Value("${password}")
	private String password;

	@Value("${port}")
	private String port;

	@Value("${username}")
	private String username;

	@Value("${binlog}")
	private String binlog;

	@Autowired
	Machine machine;

	@Autowired
	private BeanFactory beanFactory;

	@GetMapping("/")
	public String home() {
		logger.info("Welcome");
		return "Welcome";
	}

	@GetMapping("/log")
	public String getLog() throws IOException {

		machine.setHost(host);
		machine.setPassword(password);
		machine.setPort(Integer.parseInt(port));
		machine.setUsername(username);

		BinaryLogFileReader reader = beanFactory.getBean(BinaryLogFileReader.class, machine, binlog);
		try {
			for (Event event; (event = reader.readEvent()) != null;) {
				Event readEvent = reader.readEvent();
				if(readEvent !=null)
				logger.info(readEvent.toString());
			}
		} finally {
			reader.close();
		}

		return "Successfully Processed";
	}

	@GetMapping("/test")
	public String test() {
		logger.info("Test Successfull !");
		return "Test Successfull !";
	}
}
