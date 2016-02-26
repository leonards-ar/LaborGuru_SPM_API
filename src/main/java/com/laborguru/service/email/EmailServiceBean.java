/*
 * File name: EmailServiceBean.java
 * Creation date: 23/11/2008 11:55:25
 * Copyright Mindpool
 */
package com.laborguru.service.email;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class EmailServiceBean implements EmailService {
	private static final Logger log = Logger.getLogger(EmailServiceBean.class);

	private JavaMailSender mailSender;
	private SimpleMailMessage mailMessage;
	private boolean enabled;
	private VelocityEngine velocityEngine;
	
	/**
	 * 
	 */
	public EmailServiceBean() {
	}

	/**
	 * @param to
	 * @param cc
	 * @param subject
	 * @param body
	 * @see com.laborguru.service.email.EmailService#sendEmail(java.lang.String[], java.lang.String[], java.lang.String, java.lang.String)
	 */
	public void sendEmail(String[] to, String[] cc, String subject, String body) {
		if(isEnabled()) {
			SimpleMailMessage msg = new SimpleMailMessage(getMailMessage());
			msg.setTo(to);
			if(cc != null) {
				msg.setCc(cc);
			}
			msg.setSubject(subject);
			msg.setText(body);
			msg.setSentDate(new Date());
			
			if(log.isDebugEnabled()) {
				log.debug("About to send message [" + printMessage(msg) + "]");
			}
			
			getMailSender().send(msg);
			
			if(log.isDebugEnabled()) {
				log.debug("Message [" + printMessage(msg) + "] sent");
			}
		} else {
			log.warn("Email Service is disabled. Check configuration.");
		}
	}
	
	/**
	 * 
	 * @param to
	 * @param cc
	 * @param subject
	 * @param bodyTemplate
	 * @param model
	 * @param attachments
	 * @see com.laborguru.service.email.EmailService#sendHtmlEmail(java.lang.String[], java.lang.String[], java.lang.String, java.util.Map, java.util.List)
	 */
	public void sendHtmlEmail(final String[] to, final String[] cc, final String subject, final String bodyTemplate, final Map<String, Object> model, final List<String> attachments) {
		if (isEnabled()) {
			getMailSender().send(new MimeMessagePreparator() {

				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
					message.setTo(to);
					if (cc != null) {
						message.setCc(cc);
					}
					message.setFrom(getMailMessage().getFrom());
					message.setSubject(subject);
					message.setSentDate(new Date());
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, bodyTemplate, model);
					if(log.isDebugEnabled())
					{
						log.debug("Email to send: [" + text + "]");
					}
					message.setText(text, true);
				}
			});
		} else {
			log.warn("Email Service is disabled. Check configuration.");
		}
	}	

	/**
	 * 
	 * @param msg
	 * @return
	 */
	private String printMessage(SimpleMailMessage msg) {
		if(msg != null) {
			try {
				return ReflectionToStringBuilder.toString(msg);
			} catch(Throwable ex) {
				log.error("Error in reflection to string builder", ex);
				return msg.toString();
			}
		} else {
			return null;
		}
	}
	
	/**
	 * @return the mailSender
	 */
	public JavaMailSender getMailSender() {
		return mailSender;
	}

	/**
	 * @param mailSender the mailSender to set
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * @return the mailMessage
	 */
	public SimpleMailMessage getMailMessage() {
		return mailMessage;
	}

	/**
	 * @param mailMessage the mailMessage to set
	 */
	public void setMailMessage(SimpleMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

}
