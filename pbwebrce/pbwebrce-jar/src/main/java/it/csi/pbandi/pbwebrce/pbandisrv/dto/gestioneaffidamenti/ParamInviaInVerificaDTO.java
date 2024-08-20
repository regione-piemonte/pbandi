/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti;


public class ParamInviaInVerificaDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Long idAppalto;

	private Long idProgetto;

	private String codiceProgettoVisualizzato;

	private String titoloProgetto;

	private String nomeBandoLinea;

	private String beneficiario;
	
	private String descBreveRuoloEnte;

	public Long getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(Long idAppalto) {
		this.idAppalto = idAppalto;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public String getCodiceProgettoVisualizzato() {
		return codiceProgettoVisualizzato;
	}

	public void setCodiceProgettoVisualizzato(String codiceProgettoVisualizzato) {
		this.codiceProgettoVisualizzato = codiceProgettoVisualizzato;
	}

	public String getTitoloProgetto() {
		return titoloProgetto;
	}

	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}

	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}

	public String getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}

	public String getDescBreveRuoloEnte() {
		return descBreveRuoloEnte;
	}

	public void setDescBreveRuoloEnte(String descBreveRuoloEnte) {
		this.descBreveRuoloEnte = descBreveRuoloEnte;
	}

		
	
	

}
