package it.csi.pbandi.pbweb.pbandisrv.integration.services.mail.datasource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

public class AttachmentDataSource implements DataSource {
	
	private byte[] data = new byte[0];
	private String name = null;
	private String contentType = null;
	
	public AttachmentDataSource(byte[] data, String name, String contentType) {
		super();
		this.data = data;
		this.name = name;
		this.contentType = contentType;
	}

	public InputStream getInputStream() throws IOException {
		
		return new ByteArrayInputStream(data);
	}

	
	public OutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public byte[] getData() {
		return data;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	public String getContentType() {
		return contentType;
	}

}
