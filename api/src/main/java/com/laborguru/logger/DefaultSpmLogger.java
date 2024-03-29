package com.laborguru.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.LogManager;


public class DefaultSpmLogger implements SpmLogger {

	private static final String LOG_AUDITORY = "auditory";
	private static final String LOG_MSG = "message";
	private static final String LOG_ERROR = "error";
	
	private static Logger auditoryLogger = null;
	private static Logger messageLogger = null;
	private static Logger errorLogger = null;
	
	private Properties logMessagesConfig;
	
	private static DefaultSpmLogger instance = new DefaultSpmLogger();
	
	private String remoteAddress;
	private String username;
	
	private DefaultSpmLogger() {
		init();
	}
	
	
	public static DefaultSpmLogger getInstance() {
		return instance;
	}
	
	public void init() {
		auditoryLogger = LoggerFactory.getLogger(LOG_AUDITORY);
		messageLogger = LoggerFactory.getLogger(LOG_MSG);
		errorLogger = LoggerFactory.getLogger(LOG_ERROR);
	}
	
	 
	public void auditLog(String msgKey, Object[] params) {
		String msg = getStringMessage(msgKey, params);
		auditoryLogger.info(msg);
	}

	public void debugLog(String msgKey, Object[] params) {
		String msg = getStringMessage(msgKey, params);
		messageLogger.debug(msg);
	}

	public void debugLog(String msg) {
		messageLogger.debug(msg);
	}
		
	public void errorLog(String msgKey, Object[] params) {
		String msg = getStringMessage(msgKey, params);
		errorLogger.error(msg);
		
	}
	
	public void errorLog(String msg, Throwable t) {
		errorLogger.error(msg, t);
	}

	public void errorLog(String msg) {
		errorLogger.error(msg);
	}	
	
	public void infoLog(String msgKey, Object[] params) {
		String msg = getStringMessage(msgKey, params);
		messageLogger.info(msg);
		
	}

	private String getStringMessage(String msgKey, Object[] params) {
		String msgString = logMessagesConfig.getProperty(msgKey);
		String msg = MessageFormat.format(msgString, params);
		return msg;
	}

	public void setLogMessagesConfigResource(String logMessagesConfigResource) {
		this.logMessagesConfig = new Properties();
		
		try{
			logMessagesConfig.load(getClass().getResourceAsStream(logMessagesConfigResource));
		} catch(IOException e){
			throw new RuntimeException("messageCconfig file not found");
		}
	}


	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}
