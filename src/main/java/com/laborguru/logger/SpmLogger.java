package com.laborguru.logger;

public interface SpmLogger {

	public void reconfigure();

	public void debugLog(String msgKey, Object[] params);

	public void auditLog(String msgKey, Object[] params);

	public void errorLog(String msgKey, Object[] params);
	
	public void errorLog(String msg, Throwable t);

	public void infoLog(String msgKey, Object[] params);
	
	
}
