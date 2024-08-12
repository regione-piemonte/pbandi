package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;



public class EnteDiCompetenzaProgSoggVO  extends GenericVO {
	BigDecimal id_ente_competenza ;
	public BigDecimal getId_ente_competenza() {
		return id_ente_competenza;
	}
	public void setId_ente_competenza(BigDecimal id_ente_competenza) {
		this.id_ente_competenza = id_ente_competenza;
	}
	public BigDecimal getIdsoggoperante() {
		return idsoggoperante;
	}
	public void setIdsoggoperante(BigDecimal idsoggoperante) {
		this.idsoggoperante = idsoggoperante;
	}
	public BigDecimal getId_progetto() {
		return id_progetto;
	}
	public void setId_progetto(BigDecimal id_progetto) {
		this.id_progetto = id_progetto;
	}
	public String getCodice_visualizzato() {
		return codice_visualizzato;
	}
	public void setCodice_visualizzato(String codice_visualizzato) {
		this.codice_visualizzato = codice_visualizzato;
	}
	public String getDesc_breve_tipo_ente_competenz() {
		return desc_breve_tipo_ente_competenz;
	}
	public void setDesc_breve_tipo_ente_competenz(
			String desc_breve_tipo_ente_competenz) {
		this.desc_breve_tipo_ente_competenz = desc_breve_tipo_ente_competenz;
	}
	BigDecimal idsoggoperante;
	BigDecimal id_progetto;
    String codice_visualizzato;
    String desc_breve_tipo_ente_competenz;
}