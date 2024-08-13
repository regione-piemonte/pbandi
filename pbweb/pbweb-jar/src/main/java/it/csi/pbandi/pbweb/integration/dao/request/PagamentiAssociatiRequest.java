/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.request;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class PagamentiAssociatiRequest {
	
	private Long idDocumentoDiSpesa;
	private String tipoInvioDocumentoDiSpesa;	// D (digitale); C (cartaceo)
	private String descBreveStatoDocSpesa;
	private String tipoOperazioneDocSpesa;		// "inserisci", "modifica", etc
	private Long idProgetto;
	private Long idBandoLinea;
	private String codiceRuolo;
	private Boolean validazione;				// true se si è in Validazione o in ValidazioneFinale.
	
	public Long getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}
	public void setIdDocumentoDiSpesa(Long idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}
	public String getTipoInvioDocumentoDiSpesa() {
		return tipoInvioDocumentoDiSpesa;
	}
	public void setTipoInvioDocumentoDiSpesa(String tipoInvioDocumentoDiSpesa) {
		this.tipoInvioDocumentoDiSpesa = tipoInvioDocumentoDiSpesa;
	}
	public String getDescBreveStatoDocSpesa() {
		return descBreveStatoDocSpesa;
	}
	public void setDescBreveStatoDocSpesa(String descBreveStatoDocSpesa) {
		this.descBreveStatoDocSpesa = descBreveStatoDocSpesa;
	}
	public String getTipoOperazioneDocSpesa() {
		return tipoOperazioneDocSpesa;
	}
	public void setTipoOperazioneDocSpesa(String tipoOperazioneDocSpesa) {
		this.tipoOperazioneDocSpesa = tipoOperazioneDocSpesa;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdBandoLinea() {
		return idBandoLinea;
	}
	public void setIdBandoLinea(Long idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}
	public String getCodiceRuolo() {
		return codiceRuolo;
	}
	public void setCodiceRuolo(String codiceRuolo) {
		this.codiceRuolo = codiceRuolo;
	}
	public Boolean getValidazione() {
		return validazione;
	}
	public void setValidazione(Boolean validazione) {
		this.validazione = validazione;
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
