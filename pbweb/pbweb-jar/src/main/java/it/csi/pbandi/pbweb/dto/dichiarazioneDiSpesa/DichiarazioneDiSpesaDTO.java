/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class DichiarazioneDiSpesaDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.String codiceFiscaleBeneficiario = null;
	private java.lang.String codiceProgetto = null;
	private java.util.Date dataInizioRendicontazione = null;
	private java.util.Date dataFineRendicontazione = null;
	private java.lang.Long idBandoLinea = null;
	private java.lang.Long idDocIndex = null;
	private java.lang.Long idProgetto = null;
	private java.lang.Long idSoggetto = null;
	private java.lang.Boolean isRicercaPerCapofila = null;
	private java.lang.Boolean isRicercaPerPartners = null;
	private java.lang.String uuidDocumento = null;
	private java.lang.String nomeFile = null;
	private java.lang.String tipoDichiarazione = null;
	private java.lang.Long idDichiarazioneSpesa = null;
	private java.lang.String tipoInvioDs = null;
	private java.lang.Long idProgettoContributoPiuGreen = null;
	
	public java.lang.String getCodiceFiscaleBeneficiario() {
		return codiceFiscaleBeneficiario;
	}
	public void setCodiceFiscaleBeneficiario(java.lang.String codiceFiscaleBeneficiario) {
		this.codiceFiscaleBeneficiario = codiceFiscaleBeneficiario;
	}
	public java.lang.String getCodiceProgetto() {
		return codiceProgetto;
	}
	public void setCodiceProgetto(java.lang.String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
	public java.util.Date getDataInizioRendicontazione() {
		return dataInizioRendicontazione;
	}
	public void setDataInizioRendicontazione(java.util.Date dataInizioRendicontazione) {
		this.dataInizioRendicontazione = dataInizioRendicontazione;
	}
	public java.util.Date getDataFineRendicontazione() {
		return dataFineRendicontazione;
	}
	public void setDataFineRendicontazione(java.util.Date dataFineRendicontazione) {
		this.dataFineRendicontazione = dataFineRendicontazione;
	}
	public java.lang.Long getIdBandoLinea() {
		return idBandoLinea;
	}
	public void setIdBandoLinea(java.lang.Long idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}
	public java.lang.Long getIdDocIndex() {
		return idDocIndex;
	}
	public void setIdDocIndex(java.lang.Long idDocIndex) {
		this.idDocIndex = idDocIndex;
	}
	public java.lang.Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(java.lang.Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public java.lang.Long getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(java.lang.Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public java.lang.Boolean getIsRicercaPerCapofila() {
		return isRicercaPerCapofila;
	}
	public void setIsRicercaPerCapofila(java.lang.Boolean isRicercaPerCapofila) {
		this.isRicercaPerCapofila = isRicercaPerCapofila;
	}
	public java.lang.Boolean getIsRicercaPerPartners() {
		return isRicercaPerPartners;
	}
	public void setIsRicercaPerPartners(java.lang.Boolean isRicercaPerPartners) {
		this.isRicercaPerPartners = isRicercaPerPartners;
	}
	public java.lang.String getUuidDocumento() {
		return uuidDocumento;
	}
	public void setUuidDocumento(java.lang.String uuidDocumento) {
		this.uuidDocumento = uuidDocumento;
	}
	public java.lang.String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(java.lang.String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public java.lang.String getTipoDichiarazione() {
		return tipoDichiarazione;
	}
	public void setTipoDichiarazione(java.lang.String tipoDichiarazione) {
		this.tipoDichiarazione = tipoDichiarazione;
	}
	public java.lang.Long getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}
	public void setIdDichiarazioneSpesa(java.lang.Long idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
	public java.lang.String getTipoInvioDs() {
		return tipoInvioDs;
	}
	public void setTipoInvioDs(java.lang.String tipoInvioDs) {
		this.tipoInvioDs = tipoInvioDs;
	}
	public java.lang.Long getIdProgettoContributoPiuGreen() {
		return idProgettoContributoPiuGreen;
	}
	public void setIdProgettoContributoPiuGreen(java.lang.Long idProgettoContributoPiuGreen) {
		this.idProgettoContributoPiuGreen = idProgettoContributoPiuGreen;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+": ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}

}
