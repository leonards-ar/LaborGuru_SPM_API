package com.laborguru.aop;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;

public class DebuggerInterceptor implements MethodBeforeAdvice {

	private static Logger logger = LoggerFactory.getLogger(DebuggerInterceptor.class);
	
	public void before(Method method, Object[] args, Object target) throws Throwable {
		
		if(logger.isDebugEnabled()) {
			StringBuilder msg = new StringBuilder();
			msg.append(method.getName()).append(" ");
			msg.append("before select params: ");
			for(Object obj: args) {
				msg.append(obj.toString()).append(" ");
			}
			
			logger.debug(msg.toString());
		}
		
	}

}
