/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.documentoDiSpesa;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class VoceDiSpesaDTO implements java.io.Serializable {
	
	static final long serialVersionUID = 1;
	
	private java.lang.Long idVoceDiSpesa = null;
	private java.lang.Long idRigoContoEconomico = null;
	private java.lang.Long idProgetto = null;
	private java.lang.Long idQuotaParteDocSpesa = null;
	private java.lang.Long idDocSpesa = null;
	private java.lang.Double importo = null;
	private java.lang.String descVoceDiSpesa = null;
	private java.lang.Double importoRichiesto = null;
	private java.lang.Double importoAgevolato = null;
	private java.lang.Double importoFinanziamento = null;
	private java.lang.Long idVoceDiSpesaPadre = null;
	private java.lang.Double oreLavorate = null;
	private java.lang.Double costoOrario = null;
	private java.lang.Long idTipoDocumentoDiSpesa = null;
	private java.lang.String descVoceDiSpesaCompleta = null;
	private java.lang.Double importoRendicontato = null;
	private java.lang.Double importoResiduoAmmesso = null;
	private java.lang.String descVoceDiSpesaPadre = null;
	
	private java.lang.Boolean modificaAbilitata = null;
	private java.lang.Boolean cancellazioneAbilitata = null;
	
	public java.lang.Boolean getModificaAbilitata() {
		return modificaAbilitata;
	}
	public void setModificaAbilitata(java.lang.Boolean modificaAbilitata) {
		this.modificaAbilitata = modificaAbilitata;
	}
	public java.lang.Boolean getCancellazioneAbilitata() {
		return cancellazioneAbilitata;
	}
	public void setCancellazioneAbilitata(java.lang.Boolean cancellazioneAbilitata) {
		this.cancellazioneAbilitata = cancellazioneAbilitata;
	}
	public java.lang.Long getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}
	public void setIdVoceDiSpesa(java.lang.Long idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
	public java.lang.Long getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}
	public void setIdRigoContoEconomico(java.lang.Long idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}
	public java.lang.Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(java.lang.Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public java.lang.Long getIdQuotaParteDocSpesa() {
		return idQuotaParteDocSpesa;
	}
	public void setIdQuotaParteDocSpesa(java.lang.Long idQuotaParteDocSpesa) {
		this.idQuotaParteDocSpesa = idQuotaParteDocSpesa;
	}
	public java.lang.Long getIdDocSpesa() {
		return idDocSpesa;
	}
	public void setIdDocSpesa(java.lang.Long idDocSpesa) {
		this.idDocSpesa = idDocSpesa;
	}
	public java.lang.Double getImporto() {
		return importo;
	}
	public void setImporto(java.lang.Double importo) {
		this.importo = importo;
	}
	public java.lang.String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}
	public void setDescVoceDiSpesa(java.lang.String descVoceDiSpesa) {
		this.descVoceDiSpesa = descVoceDiSpesa;
	}
	public java.lang.Double getImportoRichiesto() {
		return importoRichiesto;
	}
	public void setImportoRichiesto(java.lang.Double importoRichiesto) {
		this.importoRichiesto = importoRichiesto;
	}
	public java.lang.Double getImportoAgevolato() {
		return importoAgevolato;
	}
	public void setImportoAgevolato(java.lang.Double importoAgevolato) {
		this.importoAgevolato = importoAgevolato;
	}
	public java.lang.Double getImportoFinanziamento() {
		return importoFinanziamento;
	}
	public void setImportoFinanziamento(java.lang.Double importoFinanziamento) {
		this.importoFinanziamento = importoFinanziamento;
	}
	public java.lang.Long getIdVoceDiSpesaPadre() {
		return idVoceDiSpesaPadre;
	}
	public void setIdVoceDiSpesaPadre(java.lang.Long idVoceDiSpesaPadre) {
		this.idVoceDiSpesaPadre = idVoceDiSpesaPadre;
	}
	public java.lang.Double getOreLavorate() {
		return oreLavorate;
	}
	public void setOreLavorate(java.lang.Double oreLavorate) {
		this.oreLavorate = oreLavorate;
	}
	public java.lang.Double getCostoOrario() {
		return costoOrario;
	}
	public void setCostoOrario(java.lang.Double costoOrario) {
		this.costoOrario = costoOrario;
	}
	public java.lang.Long getIdTipoDocumentoDiSpesa() {
		return idTipoDocumentoDiSpesa;
	}
	public void setIdTipoDocumentoDiSpesa(java.lang.Long idTipoDocumentoDiSpesa) {
		this.idTipoDocumentoDiSpesa = idTipoDocumentoDiSpesa;
	}
	public java.lang.String getDescVoceDiSpesaCompleta() {
		return descVoceDiSpesaCompleta;
	}
	public void setDescVoceDiSpesaCompleta(java.lang.String descVoceDiSpesaCompleta) {
		this.descVoceDiSpesaCompleta = descVoceDiSpesaCompleta;
	}
	public java.lang.Double getImportoRendicontato() {
		return importoRendicontato;
	}
	public void setImportoRendicontato(java.lang.Double importoRendicontato) {
		this.importoRendicontato = importoRendicontato;
	}
	public java.lang.Double getImportoResiduoAmmesso() {
		return importoResiduoAmmesso;
	}
	public void setImportoResiduoAmmesso(java.lang.Double importoResiduoAmmesso) {
		this.importoResiduoAmmesso = importoResiduoAmmesso;
	}
	public java.lang.String getDescVoceDiSpesaPadre() {
		return descVoceDiSpesaPadre;
	}
	public void setDescVoceDiSpesaPadre(java.lang.String descVoceDiSpesaPadre) {
		this.descVoceDiSpesaPadre = descVoceDiSpesaPadre;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nVoceDiSpesaDTO: ");
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
