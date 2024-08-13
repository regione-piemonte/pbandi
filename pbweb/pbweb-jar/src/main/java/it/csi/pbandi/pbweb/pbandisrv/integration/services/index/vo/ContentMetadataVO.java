/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.services.index.vo;

public class ContentMetadataVO {
	
	private String uid;
	private String mimeType;
	private long size;
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUid() {
		return uid;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public long getSize() {
		return size;
	}

}
