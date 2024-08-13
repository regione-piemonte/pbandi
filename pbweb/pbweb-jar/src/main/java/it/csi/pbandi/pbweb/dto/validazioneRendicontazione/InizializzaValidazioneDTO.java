/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.validazioneRendicontazione;

import java.beans.IntrospectionException;
import java.util.List;
import java.util.Set;

import it.csi.pbandi.pbweb.dto.profilazione.DecodificaDTO;
import it.csi.pbandi.pbweb.util.BeanUtil;

public class InizializzaValidazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	
	private String codiceVisualizzatoProgetto;
	
	private Boolean taskVisibile;
	
	private List<String> elencoTask;		// popolato solo se taskVisibile = true.
	
	private List<DecodificaDTO> tipiDocumentoSpesa;
	
	private List<DecodificaDTO> tipiFornitore;
	
	private List<DecodificaDTO> statiDocumentoSpesa;
	
	private Boolean documentoSpesaModificabile;
	
	// Per le DS finali è l'id doc index della CFP, altrimenti è l'id doc index della DS.
	private Long idDocumentoIndex;
	private String nomeFile;
	
	private Long idComunicazFineProg;
	private String dtComunicazFineProg;
	
	// S = Documento cartaceo, N\null = Digitale.
	private String flagFirmaCartacea;
	
	// firmata \ non firmata.
	private String statoDichiarazione;
	
	private String protocollo;
	
	private Boolean esisteRichiestaIntegrazioneAperta;
	
	private List<String> dateInvio;
	
	private Long idEnteCompetenza;
	private String descBreveEnte;
	
	private Boolean validazioneMassivaAbilitata;
	
	private Boolean richiestaIntegrazioneAbilitata;
	
	private Boolean chiusuraValidazioneAbilitata;
	
	private Boolean dichiarazioneDiSpesaFinale;
	
	private String regolaInvioDigitale;				// S \ N
	
	// +Green.
	// Per le DS finali è l'id doc index della CFP, altrimenti è l'id doc index della DS.
	private Long idComunicazFineProgPiuGreen;
	private String dtComunicazFineProgPiuGreen;
	private Long idDocumentoIndexPiuGreen;
	private String nomeFilePiuGreen;
	private String statoDichiarazionePiuGreen;
	private String flagFirmaCartaceaPiuGreen;
	private String protocolloPiuGreen;
	// +Green fine.
	
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

	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}

	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}

	public Boolean getTaskVisibile() {
		return taskVisibile;
	}

	public void setTaskVisibile(Boolean taskVisibile) {
		this.taskVisibile = taskVisibile;
	}

	public List<String> getElencoTask() {
		return elencoTask;
	}

	public void setElencoTask(List<String> elencoTask) {
		this.elencoTask = elencoTask;
	}

	public List<DecodificaDTO> getTipiDocumentoSpesa() {
		return tipiDocumentoSpesa;
	}

	public void setTipiDocumentoSpesa(List<DecodificaDTO> tipiDocumentoSpesa) {
		this.tipiDocumentoSpesa = tipiDocumentoSpesa;
	}

	public List<DecodificaDTO> getTipiFornitore() {
		return tipiFornitore;
	}

	public void setTipiFornitore(List<DecodificaDTO> tipiFornitore) {
		this.tipiFornitore = tipiFornitore;
	}

	public List<DecodificaDTO> getStatiDocumentoSpesa() {
		return statiDocumentoSpesa;
	}

	public void setStatiDocumentoSpesa(List<DecodificaDTO> statiDocumentoSpesa) {
		this.statiDocumentoSpesa = statiDocumentoSpesa;
	}

	public Boolean getDocumentoSpesaModificabile() {
		return documentoSpesaModificabile;
	}

	public void setDocumentoSpesaModificabile(Boolean documentoSpesaModificabile) {
		this.documentoSpesaModificabile = documentoSpesaModificabile;
	}

	public Long getIdDocumentoIndex() {
		return idDocumentoIndex;
	}

	public void setIdDocumentoIndex(Long idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Long getIdComunicazFineProg() {
		return idComunicazFineProg;
	}

	public void setIdComunicazFineProg(Long idComunicazFineProg) {
		this.idComunicazFineProg = idComunicazFineProg;
	}

	public String getDtComunicazFineProg() {
		return dtComunicazFineProg;
	}

	public void setDtComunicazFineProg(String dtComunicazFineProg) {
		this.dtComunicazFineProg = dtComunicazFineProg;
	}

	public String getFlagFirmaCartacea() {
		return flagFirmaCartacea;
	}

	public void setFlagFirmaCartacea(String flagFirmaCartacea) {
		this.flagFirmaCartacea = flagFirmaCartacea;
	}

	public String getStatoDichiarazione() {
		return statoDichiarazione;
	}

	public void setStatoDichiarazione(String statoDichiarazione) {
		this.statoDichiarazione = statoDichiarazione;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public Boolean getEsisteRichiestaIntegrazioneAperta() {
		return esisteRichiestaIntegrazioneAperta;
	}

	public void setEsisteRichiestaIntegrazioneAperta(Boolean esisteRichiestaIntegrazioneAperta) {
		this.esisteRichiestaIntegrazioneAperta = esisteRichiestaIntegrazioneAperta;
	}

	public Long getIdEnteCompetenza() {
		return idEnteCompetenza;
	}

	public void setIdEnteCompetenza(Long idEnteCompetenza) {
		this.idEnteCompetenza = idEnteCompetenza;
	}

	public String getDescBreveEnte() {
		return descBreveEnte;
	}

	public void setDescBreveEnte(String descBreveEnte) {
		this.descBreveEnte = descBreveEnte;
	}

	public Boolean getValidazioneMassivaAbilitata() {
		return validazioneMassivaAbilitata;
	}

	public void setValidazioneMassivaAbilitata(Boolean validazioneMassivaAbilitata) {
		this.validazioneMassivaAbilitata = validazioneMassivaAbilitata;
	}

	public Boolean getRichiestaIntegrazioneAbilitata() {
		return richiestaIntegrazioneAbilitata;
	}

	public void setRichiestaIntegrazioneAbilitata(Boolean richiestaIntegrazioneAbilitata) {
		this.richiestaIntegrazioneAbilitata = richiestaIntegrazioneAbilitata;
	}

	public Boolean getChiusuraValidazioneAbilitata() {
		return chiusuraValidazioneAbilitata;
	}

	public void setChiusuraValidazioneAbilitata(Boolean chiusuraValidazioneAbilitata) {
		this.chiusuraValidazioneAbilitata = chiusuraValidazioneAbilitata;
	}

	public Boolean getDichiarazioneDiSpesaFinale() {
		return dichiarazioneDiSpesaFinale;
	}

	public void setDichiarazioneDiSpesaFinale(Boolean dichiarazioneDiSpesaFinale) {
		this.dichiarazioneDiSpesaFinale = dichiarazioneDiSpesaFinale;
	}

	public Long getIdComunicazFineProgPiuGreen() {
		return idComunicazFineProgPiuGreen;
	}

	public void setIdComunicazFineProgPiuGreen(Long idComunicazFineProgPiuGreen) {
		this.idComunicazFineProgPiuGreen = idComunicazFineProgPiuGreen;
	}

	public String getDtComunicazFineProgPiuGreen() {
		return dtComunicazFineProgPiuGreen;
	}

	public void setDtComunicazFineProgPiuGreen(String dtComunicazFineProgPiuGreen) {
		this.dtComunicazFineProgPiuGreen = dtComunicazFineProgPiuGreen;
	}

	public Long getIdDocumentoIndexPiuGreen() {
		return idDocumentoIndexPiuGreen;
	}

	public void setIdDocumentoIndexPiuGreen(Long idDocumentoIndexPiuGreen) {
		this.idDocumentoIndexPiuGreen = idDocumentoIndexPiuGreen;
	}

	public String getNomeFilePiuGreen() {
		return nomeFilePiuGreen;
	}

	public void setNomeFilePiuGreen(String nomeFilePiuGreen) {
		this.nomeFilePiuGreen = nomeFilePiuGreen;
	}

	public String getStatoDichiarazionePiuGreen() {
		return statoDichiarazionePiuGreen;
	}

	public void setStatoDichiarazionePiuGreen(String statoDichiarazionePiuGreen) {
		this.statoDichiarazionePiuGreen = statoDichiarazionePiuGreen;
	}

	public String getFlagFirmaCartaceaPiuGreen() {
		return flagFirmaCartaceaPiuGreen;
	}

	public void setFlagFirmaCartaceaPiuGreen(String flagFirmaCartaceaPiuGreen) {
		this.flagFirmaCartaceaPiuGreen = flagFirmaCartaceaPiuGreen;
	}

	public String getProtocolloPiuGreen() {
		return protocolloPiuGreen;
	}

	public void setProtocolloPiuGreen(String protocolloPiuGreen) {
		this.protocolloPiuGreen = protocolloPiuGreen;
	}

	public String getRegolaInvioDigitale() {
		return regolaInvioDigitale;
	}

	public void setRegolaInvioDigitale(String regolaInvioDigitale) {
		this.regolaInvioDigitale = regolaInvioDigitale;
	}

	public List<String> getDateInvio() {
		return dateInvio;
	}

	public void setDateInvio(List<String> dateInvio) {
		this.dateInvio = dateInvio;
	}

}
