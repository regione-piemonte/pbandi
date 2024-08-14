/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.dto.certificazione;

public class FiltroRicercaDocumentoDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;
	private Long idLineaIntervento;
	private Long idBeneficiario;	
	private Long idProposta;
	private Long idProgetto;
	
	private Boolean isDichiarazioneFinale;
	private Boolean isRicercaProposta;
	private Boolean isChecklist;
	private Boolean isRicercaPropostaProgetto;
	private Boolean isPropostaDiCertificazione;
	
	private String[] statiProposte;
	
	public void setIdProposta(Long val) {
		idProposta = val;
	}

	public Long getIdProposta() {
		return idProposta;
	}



	public void setIdLineaIntervento(Long val) {
		idLineaIntervento = val;
	}

	public Long getIdLineaIntervento() {
		return idLineaIntervento;
	}



	public void setIdBeneficiario(Long val) {
		idBeneficiario = val;
	}

	public Long getIdBeneficiario() {
		return idBeneficiario;
	}

	
	public void setIdProgetto(Long val) {
		idProgetto = val;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	

	public void setIsChecklist(Boolean val) {
		isChecklist = val;
	}

	public Boolean getIsChecklist() {
		return isChecklist;
	}

	

	public void setIsDichiarazioneFinale(Boolean val) {
		isDichiarazioneFinale = val;
	}

	public Boolean getIsDichiarazioneFinale() {
		return isDichiarazioneFinale;
	}

	

	public void setIsRicercaProposta(Boolean val) {
		isRicercaProposta = val;
	}

	public Boolean getIsRicercaProposta() {
		return isRicercaProposta;
	}



	public void setIsRicercaPropostaProgetto(Boolean val) {
		isRicercaPropostaProgetto = val;
	}

	public Boolean getIsRicercaPropostaProgetto() {
		return isRicercaPropostaProgetto;
	}



	public void setIsPropostaDiCertificazione(Boolean val) {
		isPropostaDiCertificazione = val;
	}

	public Boolean getIsPropostaDiCertificazione() {
		return isPropostaDiCertificazione;
	}



	public void setStatiProposte(java.lang.String[] val) {
		statiProposte = val;
	}

	public java.lang.String[] getStatiProposte() {
		return statiProposte;
	}
}
