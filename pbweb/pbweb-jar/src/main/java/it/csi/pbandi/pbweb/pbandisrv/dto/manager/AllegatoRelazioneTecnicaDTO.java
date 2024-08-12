package it.csi.pbandi.pbweb.pbandisrv.dto.manager;

import java.math.BigDecimal;
import java.util.Date;

public class AllegatoRelazioneTecnicaDTO {
	private BigDecimal idProgetto;
	private String codiceProgetto;
	private BigDecimal idDichiarazioneSpesa;
	private Date dataDichiarazione;
	private BigDecimal idBeneficiario;
	private String cfBeneficiario;
	private String nomeFile;
	private byte[] bytesDocumento;
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getCodiceProgetto() {
		return codiceProgetto;
	}
	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}
	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
	public Date getDataDichiarazione() {
		return dataDichiarazione;
	}
	public void setDataDichiarazione(Date dataDichiarazione) {
		this.dataDichiarazione = dataDichiarazione;
	}
	public BigDecimal getIdBeneficiario() {
		return idBeneficiario;
	}
	public void setIdBeneficiario(BigDecimal idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}
	public String getCfBeneficiario() {
		return cfBeneficiario;
	}
	public void setCfBeneficiario(String cfBeneficiario) {
		this.cfBeneficiario = cfBeneficiario;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public void setBytesDocumento(byte[] bytesDocumento) {
		this.bytesDocumento = bytesDocumento;
	}
	public byte[] getBytesDocumento() {
		return bytesDocumento;
	}
	
	

}
