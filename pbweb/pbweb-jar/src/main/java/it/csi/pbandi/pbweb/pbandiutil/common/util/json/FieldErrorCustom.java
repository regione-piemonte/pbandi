package it.csi.pbandi.pbweb.pbandiutil.common.util.json;

import java.io.Serializable;

public class FieldErrorCustom implements Serializable {
	
	String id;
	String msg;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}