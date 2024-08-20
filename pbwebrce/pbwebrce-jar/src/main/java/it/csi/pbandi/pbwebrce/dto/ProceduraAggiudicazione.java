/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

public class ProceduraAggiudicazione implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idProceduraAggiudicaz;
	private String descProcAgg;
	private java.lang.Double importo;
	private java.lang.Double iva;
	private String codProcAgg;
	private String cigProcAgg;
	private Long idTipologiaAggiudicaz;
	private String descTipologiaAggiudicazione;
	private boolean proceduraSelezionata = false;
	private String codice;
	private String linkModifica;
	private String linkAssocia;
	
	private Boolean isModificabile;
	private StepAggiudicazione[] iter = null;
	
	
	public Long getIdProceduraAggiudicaz() {
		return idProceduraAggiudicaz;
	}
	public void setIdProceduraAggiudicaz(Long idProceduraAggiudicaz) {
		this.idProceduraAggiudicaz = idProceduraAggiudicaz;
	}
	public String getDescProcAgg() {
		return descProcAgg;
	}
	public void setDescProcAgg(String descProcAgg) {
		this.descProcAgg = descProcAgg;
	}
	public java.lang.Double getImporto() {
		return importo;
	}
	public void setImporto(java.lang.Double importo) {
		this.importo = importo;
	}
	public java.lang.Double getIva() {
		return iva;
	}
	public void setIva(java.lang.Double iva) {
		this.iva = iva;
	}
	public String getCodProcAgg() {
		return codProcAgg;
	}
	public void setCodProcAgg(String codProcAgg) {
		this.codProcAgg = codProcAgg;
	}
	public String getCigProcAgg() {
		return cigProcAgg;
	}
	public void setCigProcAgg(String cigProcAgg) {
		this.cigProcAgg = cigProcAgg;
	}
	public Long getIdTipologiaAggiudicaz() {
		return idTipologiaAggiudicaz;
	}
	public void setIdTipologiaAggiudicaz(Long idTipologiaAggiudicaz) {
		this.idTipologiaAggiudicaz = idTipologiaAggiudicaz;
	}
	public String getDescTipologiaAggiudicazione() {
		return descTipologiaAggiudicazione;
	}
	public void setDescTipologiaAggiudicazione(String descTipologiaAggiudicazione) {
		this.descTipologiaAggiudicazione = descTipologiaAggiudicazione;
	}
	public boolean isProceduraSelezionata() {
		return proceduraSelezionata;
	}
	public void setProceduraSelezionata(boolean proceduraSelezionata) {
		this.proceduraSelezionata = proceduraSelezionata;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getLinkModifica() {
		return linkModifica;
	}
	public void setLinkModifica(String linkModifica) {
		this.linkModifica = linkModifica;
	}
	public String getLinkAssocia() {
		return linkAssocia;
	}
	public void setLinkAssocia(String linkAssocia) {
		this.linkAssocia = linkAssocia;
	}
	public Boolean getIsModificabile() {
		return isModificabile;
	}
	public void setIsModificabile(Boolean isModificabile) {
		this.isModificabile = isModificabile;
	}
	
	public ProceduraAggiudicazione() {
		super();
	}
	public String toString() {
		return super.toString();
	}
	public StepAggiudicazione[] getIter() {
		return iter;
	}
	public void setIter(StepAggiudicazione[] iter) {
		this.iter = iter;
	}

	

}
