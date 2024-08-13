/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa;

public class DichiarazioneDiSpesaDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;
	private String codiceFiscaleBeneficiario = null;
	private String codiceProgetto = null;
	private java.util.Date dataInizioRendicontazione = null;
	private java.util.Date dataFineRendicontazione = null;
	private Long idBandoLinea = null;
	private Long idDocIndex = null;
	private Long idProgetto = null;
	private Long idSoggetto = null;
	private Boolean isRicercaPerCapofila = null;
	private Boolean isRicercaPerPartners = null;
	private String uuidDocumento = null;
	private String nomeFile = null;
	private String tipoDichiarazione = null;
	private Long idDichiarazioneSpesa = null;
	private String tipoInvioDs = null;
	private Long idProgettoContributoPiuGreen = null;

	public String getCodiceFiscaleBeneficiario() {
		return codiceFiscaleBeneficiario;
	}

	public void setCodiceFiscaleBeneficiario(String val) {
		codiceFiscaleBeneficiario = val;
	}

	public String getCodiceProgetto() {
		return codiceProgetto;
	}

	public void setCodiceProgetto(String val) {
		codiceProgetto = val;
	}

	public java.util.Date getDataInizioRendicontazione() {
		return dataInizioRendicontazione;
	}

	public void setDataInizioRendicontazione(java.util.Date val) {
		dataInizioRendicontazione = val;
	}

	public java.util.Date getDataFineRendicontazione() {
		return dataFineRendicontazione;
	}

	public void setDataFineRendicontazione(java.util.Date val) {
		dataFineRendicontazione = val;
	}

	public Long getIdBandoLinea() {
		return idBandoLinea;
	}

	public void setIdBandoLinea(Long val) {
		idBandoLinea = val;
	}

	public Long getIdDocIndex() {
		return idDocIndex;
	}

	public void setIdDocIndex(Long val) {
		idDocIndex = val;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long val) {
		idProgetto = val;
	}

	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long val) {
		idSoggetto = val;
	}

	public Boolean getIsRicercaPerCapofila() {
		return isRicercaPerCapofila;
	}

	public void setIsRicercaPerCapofila(Boolean val) {
		isRicercaPerCapofila = val;
	}

	public Boolean getIsRicercaPerPartners() {
		return isRicercaPerPartners;
	}

	public void setIsRicercaPerPartners(Boolean val) {
		isRicercaPerPartners = val;
	}

	public String getUuidDocumento() {
		return uuidDocumento;
	}

	public void setUuidDocumento(String val) {
		uuidDocumento = val;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String val) {
		nomeFile = val;
	}

	public String getTipoDichiarazione() {
		return tipoDichiarazione;
	}

	public void setTipoDichiarazione(String val) {
		tipoDichiarazione = val;
	}

	public Long getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	public void setIdDichiarazioneSpesa(Long val) {
		idDichiarazioneSpesa = val;
	}

	public String getTipoInvioDs() {
		return tipoInvioDs;
	}

	public void setTipoInvioDs(String val) {
		tipoInvioDs = val;
	}

	public Long getIdProgettoContributoPiuGreen() {
		return idProgettoContributoPiuGreen;
	}

	public void setIdProgettoContributoPiuGreen(Long val) {
		idProgettoContributoPiuGreen = val;
	}
}