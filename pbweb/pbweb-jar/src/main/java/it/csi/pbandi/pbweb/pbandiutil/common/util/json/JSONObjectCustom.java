package it.csi.pbandi.pbweb.pbandiutil.common.util.json;

import java.util.ArrayList;

public class JSONObjectCustom {
	
	private Object data;
	boolean success = false;
	private ArrayList<FieldErrorCustom> errors;
	private ArrayList<GlobalErrorCustom> globalErrors;
	private boolean isConfirmRequired = false;
	private String msgConfirm;
	private ArrayList<String> actionMessages;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public ArrayList<FieldErrorCustom> getErrors() {
		return errors;
	}
	public void setErrors(ArrayList<FieldErrorCustom> errors) {
		this.errors = errors;
	}
	public ArrayList<GlobalErrorCustom> getGlobalErrors() {
		return globalErrors;
	}
	public void setGlobalErrors(ArrayList<GlobalErrorCustom> globalErrors) {
		this.globalErrors = globalErrors;
	}
	public boolean isConfirmRequired() {
		return isConfirmRequired;
	}
	public void setConfirmRequired(boolean isConfirmRequired) {
		this.isConfirmRequired = isConfirmRequired;
	}
	public String getMsgConfirm() {
		return msgConfirm;
	}
	public void setMsgConfirm(String msgConfirm) {
		this.msgConfirm = msgConfirm;
	}
	public ArrayList<String> getActionMessages() {
		return actionMessages;
	}
	public void setActionMessages(ArrayList<String> actionMessages) {
		this.actionMessages = actionMessages;
	}
	

}
