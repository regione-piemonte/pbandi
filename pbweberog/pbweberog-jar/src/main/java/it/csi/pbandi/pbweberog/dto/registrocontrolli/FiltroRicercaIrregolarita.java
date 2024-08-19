/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.registrocontrolli;

public class FiltroRicercaIrregolarita implements java.io.Serializable {

	private String idBeneficiario;
	private String idProgetto;
	private String dtComunicazioneIrregolarita;
	private String idTipoIrregolarita;
	private String idQualificazioneIrregolarita;
	private String idMetodoIndividuazioneIrregolarita;
	private String idStatoAmministrativo;
	private String idStatoFinanziario;
	private String idDisposizioneComunitariaTrasgredita;
	private String idNaturaSanzioneApplicata;

	private static final long serialVersionUID = 1L;

	
	
	
	public String getIdBeneficiario() {
		return idBeneficiario;
	}

	public void setIdBeneficiario(String idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}

	public String getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(String idProgetto) {
		this.idProgetto = idProgetto;
	}

	public String getDtComunicazioneIrregolarita() {
		return dtComunicazioneIrregolarita;
	}

	public void setDtComunicazioneIrregolarita(String dtComunicazioneIrregolarita) {
		this.dtComunicazioneIrregolarita = dtComunicazioneIrregolarita;
	}

	public String getIdTipoIrregolarita() {
		return idTipoIrregolarita;
	}

	public void setIdTipoIrregolarita(String idTipoIrregolarita) {
		this.idTipoIrregolarita = idTipoIrregolarita;
	}

	public String getIdQualificazioneIrregolarita() {
		return idQualificazioneIrregolarita;
	}

	public void setIdQualificazioneIrregolarita(String idQualificazioneIrregolarita) {
		this.idQualificazioneIrregolarita = idQualificazioneIrregolarita;
	}

	public String getIdMetodoIndividuazioneIrregolarita() {
		return idMetodoIndividuazioneIrregolarita;
	}

	public void setIdMetodoIndividuazioneIrregolarita(String idMetodoIndividuazioneIrregolarita) {
		this.idMetodoIndividuazioneIrregolarita = idMetodoIndividuazioneIrregolarita;
	}

	public String getIdStatoAmministrativo() {
		return idStatoAmministrativo;
	}

	public void setIdStatoAmministrativo(String idStatoAmministrativo) {
		this.idStatoAmministrativo = idStatoAmministrativo;
	}

	public String getIdStatoFinanziario() {
		return idStatoFinanziario;
	}

	public void setIdStatoFinanziario(String idStatoFinanziario) {
		this.idStatoFinanziario = idStatoFinanziario;
	}

	public String getIdDisposizioneComunitariaTrasgredita() {
		return idDisposizioneComunitariaTrasgredita;
	}

	public void setIdDisposizioneComunitariaTrasgredita(String idDisposizioneComunitariaTrasgredita) {
		this.idDisposizioneComunitariaTrasgredita = idDisposizioneComunitariaTrasgredita;
	}

	public String getIdNaturaSanzioneApplicata() {
		return idNaturaSanzioneApplicata;
	}

	public void setIdNaturaSanzioneApplicata(String idNaturaSanzioneApplicata) {
		this.idNaturaSanzioneApplicata = idNaturaSanzioneApplicata;
	}

	public FiltroRicercaIrregolarita() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
