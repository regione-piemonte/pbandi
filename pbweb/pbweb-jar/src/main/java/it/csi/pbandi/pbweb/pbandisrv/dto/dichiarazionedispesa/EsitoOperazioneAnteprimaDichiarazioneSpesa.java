package it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa;

public class EsitoOperazioneAnteprimaDichiarazioneSpesa implements java.io.Serializable {
	static final long serialVersionUID = 1;
	private Boolean esito = null;
	private String nomeFile = null;
	private byte[] pdfBytes = null;
	private DichiarazioneDiSpesaDTO dichiarazioneDiSpesa = null;
	private String msg = null;

	public Boolean getEsito() {
		return esito;
	}

	public void setEsito(Boolean esito) {
		this.esito = esito;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public byte[] getPdfBytes() {
		return pdfBytes;
	}

	public void setPdfBytes(byte[] pdfBytes) {
		this.pdfBytes = pdfBytes;
	}

	public DichiarazioneDiSpesaDTO getDichiarazioneDiSpesa() {
		return dichiarazioneDiSpesa;
	}

	public void setDichiarazioneDiSpesa(DichiarazioneDiSpesaDTO dichiarazioneDiSpesa) {
		this.dichiarazioneDiSpesa = dichiarazioneDiSpesa;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}