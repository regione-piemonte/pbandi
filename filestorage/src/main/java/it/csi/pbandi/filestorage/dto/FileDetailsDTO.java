/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.filestorage.dto;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Date;

public class FileDetailsDTO implements Serializable {
	/**
	 * Questa classe contiene i dettagli del file 
	 */
	private static final long serialVersionUID = 1L;
	
	private Path fileName;
	private long size;
	private Date creationDate;
	private Date lastModified;
	private Date lastAccess;
	
	public Path getFileName() {
		return fileName;
	}
	public void setFileName(Path fileName) {
		this.fileName = fileName;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public Date getLastAccess() {
		return lastAccess;
	}
	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}
	
	@Override
	public String toString() {
		return "FileDetailsDTO [fileName=" + fileName + ", size=" + size + ", creationDate=" + creationDate
				+ ", lastModified=" + lastModified + ", lastAccess=" + lastAccess + "]";
	}

}
