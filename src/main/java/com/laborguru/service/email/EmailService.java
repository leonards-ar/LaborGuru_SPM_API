/*
 * File name: EmailService.java
 * Creation date: 23/11/2008 11:53:29
 * Copyright Mindpool
 */
package com.laborguru.service.email;

import java.util.List;
import java.util.Map;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface EmailService {

	/**
	 * 
	 * @param to
	 * @param cc
	 * @param subject
	 * @param body
	 */
	void sendEmail(String[] to, String[] cc, String subject, String body);
	
	/**
	 * 
	 * @param to
	 * @param cc
	 * @param subject
	 * @param bodyTemplate
	 * @param model
	 * @param attachments
	 */
	void sendHtmlEmail(String[] to, String[] cc, String subject, String bodyTemplate, Map<String, Object> model, List<String> attachments);
}
