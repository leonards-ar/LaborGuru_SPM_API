package com.laborguru.webservice.server.endpoint.adapter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.transform.TransformerFactory;

import org.springframework.oxm.GenericUnmarshaller;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.MethodEndpoint;
import org.springframework.ws.server.endpoint.adapter.AbstractMethodEndpointAdapter;
import org.springframework.ws.server.endpoint.support.PayloadRootUtils;
import org.springframework.ws.support.MarshallingUtils;

/**
 * 
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.1
 * @since SPM 1.1
 * 
 */
public class PayloadRootAnnotationMethodEndPointAdapter extends
		AbstractMethodEndpointAdapter {

	private HashMap<String, Marshaller> marshallers;
	private HashMap<String, Unmarshaller> unmarshallers;
	private static TransformerFactory transformerFactory;

	static {
		transformerFactory = TransformerFactory.newInstance();
	}

	public PayloadRootAnnotationMethodEndPointAdapter(
			HashMap<String, Marshaller> marshallers) {

		this.marshallers = marshallers;
		this.unmarshallers = new HashMap<String, Unmarshaller>();
		for (String key : marshallers.keySet()) {
			unmarshallers.put(key, (Unmarshaller) marshallers.get(key));
		}
	}

	public PayloadRootAnnotationMethodEndPointAdapter(
			HashMap<String, Marshaller> marshallers,
			HashMap<String, Unmarshaller> unmarshallers) {
		this.marshallers = marshallers;
		this.unmarshallers = unmarshallers;
	}

	private Object unmarshalRequest(WebServiceMessage request)
			throws IOException, Exception {

		Object requestObject = MarshallingUtils.unmarshal(
				getUnmarshaller(getLocalPart(request)), request);

		if (logger.isDebugEnabled()) {
			logger.debug("Unmarshalled payload request to [" + requestObject
					+ "]");
		}

		return requestObject;
	}

	protected void invokeInternal(MessageContext messageContext, MethodEndpoint methodEndpoint) throws Exception {
		WebServiceMessage request = messageContext.getRequest();
		Object requestObject = unmarshalRequest(request);
		Object responseObject = methodEndpoint.invoke(new Object[] { requestObject });
		if (responseObject != null) {
			WebServiceMessage response = messageContext.getResponse();
			marshalResponse(responseObject, response, getLocalPart(request));
		}
	}

	private void marshalResponse(Object responseObject, WebServiceMessage response, String localPart) throws IOException, Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Marshalling [" + responseObject + "] to response payload");
		}
		MarshallingUtils.marshal(getMarshaller(localPart), responseObject, response);
	}

	/**
	 * @param methodEndpoint
	 * @return
	 * @see org.springframework.ws.server.endpoint.adapter.AbstractMethodEndpointAdapter#supportsInternal(org.springframework.ws.server.endpoint.MethodEndpoint)
	 */
	@Override
	protected boolean supportsInternal(MethodEndpoint methodEndpoint) {
		Method method = methodEndpoint.getMethod();
		return supportsReturnType(method) && supportsParameters(method);
	}

	private boolean supportsReturnType(Method method) {
		boolean support = false;
		if (Void.TYPE.equals(method.getReturnType())) {
			return true;
		} else {
			Iterator<Marshaller> it = marshallers.values().iterator();
			while (it.hasNext() && support == false) {
				Marshaller marshaller = it.next();
				if (marshaller instanceof GenericUnmarshaller) {
					GenericUnmarshaller genericUnmarshaller = (GenericUnmarshaller) marshaller;
					support = genericUnmarshaller.supports(method.getGenericParameterTypes()[0]);
				} else {
					support = marshaller.supports(method.getParameterTypes()[0]);
				}
			}
		}
		return support;
	}

	private boolean supportsParameters(Method method) {
		boolean support = false;
		if (method.getParameterTypes().length >= 1) {
			Iterator<Unmarshaller> it = unmarshallers.values().iterator();
			while (it.hasNext() && support == false) {
				Unmarshaller unmarshaller = it.next();
				if (unmarshaller instanceof GenericUnmarshaller) {
					GenericUnmarshaller genericUnmarshaller = (GenericUnmarshaller) unmarshaller;
					support = genericUnmarshaller.supports(method.getGenericParameterTypes()[0]);
				} else {
					support = unmarshaller.supports(method.getParameterTypes()[0]);
				}
			}
		}
		return support;
	}

	private Unmarshaller getUnmarshaller(String localPart)	throws Exception {
			return unmarshallers.get(localPart);
	}

	private Marshaller getMarshaller(String localPart) throws Exception {
			return marshallers.get(localPart);
	}
	
	private String getLocalPart(WebServiceMessage message) throws Exception{
		QName qname = PayloadRootUtils.getPayloadRootQName(message.getPayloadSource(), transformerFactory);
		return qname.getLocalPart();
	}
}
