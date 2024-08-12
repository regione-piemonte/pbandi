package it.csi.pbandi.pbweb.pbandisrv.dto.manager;

import java.math.BigDecimal;
import java.util.Date;

public class CheckListDocumentaleDTO {
	
	private byte [] bytesModuloPdf;
	
	private String codiceProgetto;

	private BigDecimal idDichiarazioneSpesa;

	private BigDecimal idProgetto;
	
	private BigDecimal versione;

	private String nomeFile;
	
	private String uid;
	
	private Date dataChiusura;
	
	
	public void setBytesModuloPdf(byte [] bytesModuloPdf) {
		this.bytesModuloPdf = bytesModuloPdf;
	}

	public byte [] getBytesModuloPdf() {
		return bytesModuloPdf;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdDichiarazioneDiSpesa(BigDecimal idDichiarazioneDiSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneDiSpesa;
	}

	public BigDecimal getIdDichiarazioneDiSpesa() {
		return idDichiarazioneSpesa;
	}

	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneDiSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneDiSpesa;
	}

	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}

	public String getCodiceProgetto() {
		return codiceProgetto;
	}

	public void setDataChiusura(Date dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public Date getDataChiusura() {
		return dataChiusura;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		return uid;
	}

	public void setVersione(BigDecimal versione) {
		this.versione = versione;
	}

	public BigDecimal getVersione() {
		return versione;
	}

}
