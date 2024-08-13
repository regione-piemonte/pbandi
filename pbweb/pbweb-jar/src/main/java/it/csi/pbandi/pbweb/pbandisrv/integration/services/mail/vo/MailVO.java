/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.services.mail.vo;

import java.util.ArrayList;
import java.util.List;

public class MailVO {
	public enum Priority {HIGH, NORMAL};
	private List<String> toAddresses;
	private List<String> ccAddresses;
	private String fromAddress;
	private String subject;
	private String content;
	private List<AttachmentVO> attachments;
	private Priority priority = Priority.NORMAL;

	public String getToAddress() {
		if (toAddresses != null) {
			return toAddresses.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Non si dovrebbe presupporre il numero di mail coinvolte (1) 
	 */
	@Deprecated
	public void setToAddress(String toAddress) {
		toAddresses = new ArrayList<String>();
		toAddresses.add(toAddress);
	}

	/**
	 * Non si dovrebbe presupporre il numero di mail coinvolte (1) 
	 */
	@Deprecated
	public String getCcAddress() {
		if (ccAddresses != null) {
			return ccAddresses.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Non si dovrebbe presupporre il numero di mail coinvolte (1) 
	 */
	@Deprecated
	public void setCcAddress(String ccAddress) {
		ccAddresses = new ArrayList<String>();
		ccAddresses.add(ccAddress);
	}
	
	public List<String> getToAddresses() {
		return toAddresses;
	}

	public void setToAddresses(List<String> toAddresses) {
		this.toAddresses = toAddresses;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setAttachments(List<AttachmentVO> attachments) {
		this.attachments = attachments;
	}

	public List<AttachmentVO> getAttachments() {
		return attachments;
	}

	public void setCcAddresses(List<String> ccAddresses) {
		this.ccAddresses = ccAddresses;
	}

	public List<String> getCcAddresses() {
		return ccAddresses;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Priority getPriority() {
		return priority;
	}
}
