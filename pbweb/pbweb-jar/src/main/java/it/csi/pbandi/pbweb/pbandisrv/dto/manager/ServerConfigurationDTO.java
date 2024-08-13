/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.manager;

import java.math.BigDecimal;

public class ServerConfigurationDTO {
	private String indirizzoEmailSupporto;
	private String indirizzoEmailTemplate;
	private String contextSuffix;
	private BigDecimal documentLockTimeout;


	public void setIndirizzoEmailSupporto(String indirizzoEmailSupporto) {
		this.indirizzoEmailSupporto = indirizzoEmailSupporto;
	}

	public String getIndirizzoEmailSupporto() {
		return indirizzoEmailSupporto;
	}

	public void setContextSuffix(String contextSuffix) {
		this.contextSuffix = contextSuffix;
	}

	public String getContextSuffix() {
		return contextSuffix;
	}

	public void setDocumentLockTimeout(BigDecimal documentLockTimeout) {
		this.documentLockTimeout = documentLockTimeout;
	}

	public BigDecimal getDocumentLockTimeout() {
		return documentLockTimeout;
	}

	public void setIndirizzoEmailTemplate(String indirizzoEmailTemplate) {
		this.indirizzoEmailTemplate = indirizzoEmailTemplate;
	}

	public String getIndirizzoEmailTemplate() {
		return indirizzoEmailTemplate;
	}
}
