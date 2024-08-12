package it.csi.pbandi.pbweb.pbandisrv.dto.manager;

import java.math.BigDecimal;

public class AllegatoCheckListClDTO {
	
	private BigDecimal idProgetto;
	private String codiceProgetto;
	private BigDecimal idChecklist;
	private String nomeFile;
	private byte[] bytesDocumento;

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdChecklist(BigDecimal idChecklist) {
		this.idChecklist = idChecklist;
	}

	public BigDecimal getIdChecklist() {
		return idChecklist;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setBytesDocumento(byte[] bytesDocumento) {
		this.bytesDocumento = bytesDocumento;
	}

	public byte[] getBytesDocumento() {
		return bytesDocumento;
	}

	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}

	public String getCodiceProgetto() {
		return codiceProgetto;
	}
}
