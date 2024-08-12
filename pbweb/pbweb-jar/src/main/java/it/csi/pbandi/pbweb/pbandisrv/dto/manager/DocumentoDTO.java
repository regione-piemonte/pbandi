package it.csi.pbandi.pbweb.pbandisrv.dto.manager;

public class DocumentoDTO {
	private String nomeFile;
	private String mimeType;
	private byte[] bytesDocumento;

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setBytesDocumento(byte[] bytesDocumento) {
		this.bytesDocumento = bytesDocumento;
	}

	public byte[] getBytesDocumento() {
		return bytesDocumento;
	}
}
