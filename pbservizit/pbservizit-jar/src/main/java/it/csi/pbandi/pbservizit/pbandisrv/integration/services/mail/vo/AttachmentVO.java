/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.mail.vo;

public class AttachmentVO {
	
	private byte[] data ;
	private String contentType;
	private String name;
	
	public void setData(byte[] data) {
		this.data = data;
	}
	public byte[] getData() {
		return data;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getContentType() {
		return contentType;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

}
