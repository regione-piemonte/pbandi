/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import it.csi.pbandi.pbwebrce.pbandiutil.common.BeanUtil;



public class ResponseCodeMessage implements Serializable {
	
	private static final long serialVersionUID = -1;
	
	private String code;
	private String message;	
	
	public ResponseCodeMessage(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
		
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}



	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+": ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				if ("class".equalsIgnoreCase(propName))
					continue;
				sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}
	
}
