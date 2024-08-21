/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.indicatori;

public class IndicatoreItem implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Long idIndicatore;
	private Long idTipoIndicatore;
	private String descIndicatore;
	private String codIgrue;
	private String descTipoIndicatore;
	private String valoreIniziale;
	private String valoreAggiornamento;
	private String valoreFinale;
	private String descUnitaMisura;
	private boolean isTipoIndicatore = false;
	private boolean isValoreInizialeEditable = false;
	private boolean isValoreAggiornamentoEditable = false;
	private boolean isValoreFinaleEditable = false;
	private String label;
	private boolean flagObbligatorio = false;
	private String codiceErrore;
	private Boolean flagNonApplicabile;
	private Boolean isFlagNonApplicabileEditabile;
	private Long idBando;

	private String infoIniziale;
	private String infoFinale;
	
	
	public String getInfoIniziale() {
		return infoIniziale;
	}

	public void setInfoIniziale(String infoIniziale) {
		this.infoIniziale = infoIniziale;
	}
	
	public String getInfoFinale() {
		return infoFinale;
	}

	public void setInfoFinale(String infoFinale) {
		this.infoFinale = infoFinale;
	}
	
	
	public Long getIdIndicatore() {
		return idIndicatore;
	}

	public void setIdIndicatore(Long idIndicatore) {
		this.idIndicatore = idIndicatore;
	}

	public Long getIdTipoIndicatore() {
		return idTipoIndicatore;
	}

	public void setIdTipoIndicatore(Long idTipoIndicatore) {
		this.idTipoIndicatore = idTipoIndicatore;
	}

	public String getDescIndicatore() {
		return descIndicatore;
	}

	public void setDescIndicatore(String descIndicatore) {
		this.descIndicatore = descIndicatore;
	}

	public String getCodIgrue() {
		return codIgrue;
	}

	public void setCodIgrue(String codIgrue) {
		this.codIgrue = codIgrue;
	}

	public String getDescTipoIndicatore() {
		return descTipoIndicatore;
	}

	public void setDescTipoIndicatore(String descTipoIndicatore) {
		this.descTipoIndicatore = descTipoIndicatore;
	}

	public String getValoreIniziale() {
		return valoreIniziale;
	}

	public void setValoreIniziale(String valoreIniziale) {
		this.valoreIniziale = valoreIniziale;
	}

	public String getValoreAggiornamento() {
		return valoreAggiornamento;
	}

	public void setValoreAggiornamento(String valoreAggiornamento) {
		this.valoreAggiornamento = valoreAggiornamento;
	}

	public String getValoreFinale() {
		return valoreFinale;
	}

	public void setValoreFinale(String valoreFinale) {
		this.valoreFinale = valoreFinale;
	}

	public String getDescUnitaMisura() {
		return descUnitaMisura;
	}

	public void setDescUnitaMisura(String descUnitaMisura) {
		this.descUnitaMisura = descUnitaMisura;
	}

	public boolean getIsTipoIndicatore() {
		return isTipoIndicatore;
	}

	public void setIsTipoIndicatore(boolean isTipoIndicatore) {
		this.isTipoIndicatore = isTipoIndicatore;
	}

	public boolean getIsValoreInizialeEditable() {
		return isValoreInizialeEditable;
	}

	public void setIsValoreInizialeEditable(boolean isValoreInizialeEditable) {
		this.isValoreInizialeEditable = isValoreInizialeEditable;
	}

	public boolean getIsValoreAggiornamentoEditable() {
		return isValoreAggiornamentoEditable;
	}

	public void setIsValoreAggiornamentoEditable(boolean isValoreAggiornamentoEditable) {
		this.isValoreAggiornamentoEditable = isValoreAggiornamentoEditable;
	}

	public boolean getIsValoreFinaleEditable() {
		return isValoreFinaleEditable;
	}

	public void setIsValoreFinaleEditable(boolean isValoreFinaleEditable) {
		this.isValoreFinaleEditable = isValoreFinaleEditable;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isFlagObbligatorio() {
		return flagObbligatorio;
	}

	public void setFlagObbligatorio(boolean flagObbligatorio) {
		this.flagObbligatorio = flagObbligatorio;
	}

	public String getCodiceErrore() {
		return codiceErrore;
	}

	public void setCodiceErrore(String codiceErrore) {
		this.codiceErrore = codiceErrore;
	}

	public Boolean getFlagNonApplicabile() {
		return flagNonApplicabile;
	}

	public void setFlagNonApplicabile(Boolean flagNonApplicabile) {
		this.flagNonApplicabile = flagNonApplicabile;
	}

	public Boolean getIsFlagNonApplicabileEditabile() {
		return isFlagNonApplicabileEditabile;
	}

	public void setIsFlagNonApplicabileEditabile(Boolean isFlagNonApplicabileEditabile) {
		this.isFlagNonApplicabileEditabile = isFlagNonApplicabileEditabile;
	}

	public Long getIdBando() {
		return idBando;
	}

	public void setIdBando(Long idBando) {
		this.idBando = idBando;
	}

	public IndicatoreItem() {
		super();
	}

	public String toString() {
		return super.toString();
	}
}
