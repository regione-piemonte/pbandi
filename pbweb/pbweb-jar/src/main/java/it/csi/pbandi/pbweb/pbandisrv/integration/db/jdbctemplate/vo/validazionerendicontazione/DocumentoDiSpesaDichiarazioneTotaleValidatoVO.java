package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class DocumentoDiSpesaDichiarazioneTotaleValidatoVO extends GenericVO {
	
	private BigDecimal idProgetto;
	private BigDecimal idDichiarazioneSpesa;
	private BigDecimal idDocumentoDiSpesa;
	private BigDecimal importoTotaleValidato;
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}
	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
	public BigDecimal getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}
	public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}
	public BigDecimal getImportoTotaleValidato() {
		return importoTotaleValidato;
	}
	public void setImportoTotaleValidato(BigDecimal importoTotaleValidato) {
		this.importoTotaleValidato = importoTotaleValidato;
	}
	
}
