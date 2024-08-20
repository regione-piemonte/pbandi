/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ExecResults {
	private String resultCode;
	private Map<String, String> fldErrors = new HashMap<String, String>();
	private Collection<String> globalErrors = new ArrayList<String>();
	private Collection<String> globalMessages = new ArrayList<String>();
	private Object model;

	public void setModel(Object model) {
		this.model = model;
	}

	public Object getModel() {
		return model;
	}

	public void setResultCode(String code) {
		resultCode = code;
	}

	public String getResultCode() {
		return resultCode;
	}

	public Map<String, String> getFldErrors() {
		return fldErrors;
	}

	public void setFldErrors(Map<String, String> fldErrors) {
		this.fldErrors = fldErrors;
	}

	public Collection<String> getGlobalErrors() {
		return globalErrors;
	}

	public void setGlobalErrors(Collection<String> globalErrors) {
		this.globalErrors = globalErrors;
	}

	public Collection<String> getGlobalMessages() {
		return globalMessages;
	}

	public void setGlobalMessages(Collection<String> globalMessages) {
		this.globalMessages = globalMessages;
	}
}
