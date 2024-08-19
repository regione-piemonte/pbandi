/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.disimpegni;

public class RevocaIrregolaritaDTO implements java.io.Serializable {
	
	static final long serialVersionUID = 1;
	private Long idRevoca;
	private Long idIrregolarita;
	private Double quotaIrreg;
	private String notePraticaUsata;
	private String tipoIrregolarita;
	private DettaglioRevocaIrregolaritaDTO[] dettagliRevocaIrregolarita;
	private Double importoIrregolarita;
	private java.util.Date dtFineValidita;
	private String descPeriodoVisualizzata;
	private String motivoRevocaIrregolarita;
	private Double importoAgevolazioneIrreg;
	public Long getIdRevoca() {
		return idRevoca;
	}
	public void setIdRevoca(Long idRevoca) {
		this.idRevoca = idRevoca;
	}
	public Long getIdIrregolarita() {
		return idIrregolarita;
	}
	public void setIdIrregolarita(Long idIrregolarita) {
		this.idIrregolarita = idIrregolarita;
	}
	public Double getQuotaIrreg() {
		return quotaIrreg;
	}
	public void setQuotaIrreg(Double quotaIrreg) {
		this.quotaIrreg = quotaIrreg;
	}
	public String getNotePraticaUsata() {
		return notePraticaUsata;
	}
	public void setNotePraticaUsata(String notePraticaUsata) {
		this.notePraticaUsata = notePraticaUsata;
	}
	public String getTipoIrregolarita() {
		return tipoIrregolarita;
	}
	public void setTipoIrregolarita(String tipoIrregolarita) {
		this.tipoIrregolarita = tipoIrregolarita;
	}
	public DettaglioRevocaIrregolaritaDTO[] getDettagliRevocaIrregolarita() {
		return dettagliRevocaIrregolarita;
	}
	public void setDettagliRevocaIrregolarita(DettaglioRevocaIrregolaritaDTO[] dettagliRevocaIrregolarita) {
		this.dettagliRevocaIrregolarita = dettagliRevocaIrregolarita;
	}
	public Double getImportoIrregolarita() {
		return importoIrregolarita;
	}
	public void setImportoIrregolarita(Double importoIrregolarita) {
		this.importoIrregolarita = importoIrregolarita;
	}
	public java.util.Date getDtFineValidita() {
		return dtFineValidita;
	}
	public void setDtFineValidita(java.util.Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	public String getDescPeriodoVisualizzata() {
		return descPeriodoVisualizzata;
	}
	public void setDescPeriodoVisualizzata(String descPeriodoVisualizzata) {
		this.descPeriodoVisualizzata = descPeriodoVisualizzata;
	}
	public String getMotivoRevocaIrregolarita() {
		return motivoRevocaIrregolarita;
	}
	public void setMotivoRevocaIrregolarita(String motivoRevocaIrregolarita) {
		this.motivoRevocaIrregolarita = motivoRevocaIrregolarita;
	}
	public Double getImportoAgevolazioneIrreg() {
		return importoAgevolazioneIrreg;
	}
	public void setImportoAgevolazioneIrreg(Double importoAgevolazioneIrreg) {
		this.importoAgevolazioneIrreg = importoAgevolazioneIrreg;
	}

	
	

}
