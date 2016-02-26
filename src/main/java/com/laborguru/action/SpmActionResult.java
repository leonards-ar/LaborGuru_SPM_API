package com.laborguru.action;

/**
 * This Enum is a helper class that contains constants for common action result names.
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public enum SpmActionResult {
	LIST("list"),
	LISTACTION("listAction"),
	REMOVE("remove"),
	EDIT("edit"),
	SHOW("show"),
	DELETE("delte"),
	SAVE("save"), 
	PRINT("print"), 
	INPUT("input"),
	LOGOUT("logout"),
	LOGIN("login"),
	SUCCESS("success"),
	CANCEL_EDIT("cancelEdit"),
	CANCEL_SHOW("cancelShow"),
	CANCEL("cancel"),
	RESTART("restart"),
	STEP_1("step1"),
	STEP_2("step2"),
	STEP_3("step3"),
	STEP_4("step4"),
	STEP_5("step5"),
	STEP_6("step6"),
	STEP_7("step7"),
	STEP_8("step8");

	private String result;
	
	private SpmActionResult(String result){
		this.result = result;
	}
	
	public String getResult(){
		return this.result;
	}
}
