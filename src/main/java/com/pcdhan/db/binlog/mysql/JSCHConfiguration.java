package com.pcdhan.db.binlog.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@Configuration
public class JSCHConfiguration {

	@Autowired
	JSch jsch;

	@Bean
	@Scope(value = "prototype")
	public ChannelSftp channelSftp(Machine machine) throws JSchException {
		Session session = jsch.getSession(machine.getUsername(), machine.getHost(), machine.getPort());
		session.setConfig("StrictHostKeyChecking", "no");
		session.setPassword(machine.getPassword());
		session.setTimeout(10000);
		session.connect();
		return (ChannelSftp) session.openChannel("sftp");
	}

	@Bean
	public JSch jsch() {
		return new JSch();
	}
}