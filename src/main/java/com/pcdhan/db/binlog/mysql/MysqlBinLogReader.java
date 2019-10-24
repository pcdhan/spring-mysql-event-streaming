package com.pcdhan.db.binlog.mysql;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.github.shyiko.mysql.binlog.BinaryLogFileReader;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

@Configuration
public class MysqlBinLogReader {

	@Autowired
	private BeanFactory beanFactory;

	private final Logger logger = LoggerFactory.getLogger(MysqlBinLogReader.class);

	@Bean(name = "binaryLogFileReader")
	@Scope(value = "prototype")
	public BinaryLogFileReader binaryLogFileReader(Machine machine, String binLogPath)
			throws SftpException, JSchException, IOException {

		ChannelSftp sftp = beanFactory.getBean(ChannelSftp.class, machine);
		sftp.connect();
		logger.info("Processing Log from {} and file location is: {}", machine.getHost(), binLogPath);
		if (Files.exists(FileSystems.getDefault().getPath(binLogPath), LinkOption.NOFOLLOW_LINKS)) {
			logger.info("File does not exists {}", binLogPath);
		} else {
			logger.info("File does exists {}", binLogPath);
		}
		InputStream inputStream = sftp.get(binLogPath);
		EventDeserializer eventDeserializer = new EventDeserializer();
		eventDeserializer.setCompatibilityMode(EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
				EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY);
		BinaryLogFileReader reader = new BinaryLogFileReader(inputStream, eventDeserializer);
		return reader;
	}
}
