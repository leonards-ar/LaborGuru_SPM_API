package com.laborguru.frontend.struts2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.struts2.interceptor.StrutsConversionErrorInterceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.XWorkConverter;

/**
 * Custom interceptor to handle the error conversion.
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class SpmConversionErrorInterceptor extends StrutsConversionErrorInterceptor {

	private static final long serialVersionUID = 1L;

    /**
     * Overrides default implementation
     * 
     * @param invocation
     * @param value
     * @return
     * @see org.apache.struts2.interceptor.StrutsConversionErrorInterceptor#getOverrideExpr(com.opensymphony.xwork2.ActionInvocation, java.lang.Object)
     */
    protected Object getOverrideExpr(ActionInvocation invocation, Object value) {
        ValueStack stack = invocation.getStack();

        try {
            stack.push(value);

            return "'" + stack.findValue("top", String.class) + "'";
        } finally {
            stack.pop();
        }
    }
    /**
     * This fi
     * @param invocation
     * @return
     * @throws Exception
     * @see com.opensymphony.xwork2.interceptor.ConversionErrorInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
     */
    public String intercept(ActionInvocation invocation) throws Exception {

        ActionContext invocationContext = invocation.getInvocationContext();
        Map conversionErrors = invocationContext.getConversionErrors();
        ValueStack stack = invocationContext.getValueStack();

        HashMap fakie = null;
        
        Iterator iterator = conversionErrors.entrySet().iterator();
        
        while(iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String propertyName = (String) entry.getKey();
            Object value = entry.getValue();

            if (shouldAddError(propertyName, value)) {
                String message = XWorkConverter.getConversionErrorMessage(propertyName, stack);

                Object action = invocation.getAction();
                if (action instanceof ValidationAware) {
                    ValidationAware va = (ValidationAware) action;
                    va.addFieldError(propertyName, message);
                }

                if (fakie == null) {
                    fakie = new HashMap();
                }

                Object valueObject = getOverrideExpr(invocation, value);
                fakie.put(propertyName, valueObject);
            }
        }

        if (fakie != null) {
            // if there were some errors, put the original (fake) values in place right before the result
            stack.getContext().put(ORIGINAL_PROPERTY_OVERRIDE, fakie);
            invocation.addPreResultListener(new PreResultListener() {
                public void beforeResult(ActionInvocation invocation, String resultCode) {
                    Map fakie = (Map) invocation.getInvocationContext().get(ORIGINAL_PROPERTY_OVERRIDE);

                    if (fakie != null) {
                        invocation.getStack().setExprOverrides(fakie);
                    }
                }
            });
        }
        return invocation.invoke();
    }   
    
    /**
     * @param propertyName
     * @param value
     * @return
     * @see org.apache.struts2.interceptor.StrutsConversionErrorInterceptor#shouldAddError(java.lang.String, java.lang.Object)
     */
    protected boolean shouldAddError(String propertyName, Object value) {
    	return false;
    }
 
}
