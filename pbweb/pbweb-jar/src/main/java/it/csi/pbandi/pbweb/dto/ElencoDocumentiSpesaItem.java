/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class ElencoDocumentiSpesaItem implements java.io.Serializable {

	private java.lang.String tipologia = null;
	private java.lang.String estremi = null;
	private java.lang.String fornitore = null;
	private java.lang.Double importo = null;
	private java.lang.Double validato = null;
	private java.lang.String stato = null;
	private java.lang.String progetto = null;
	private java.lang.String idDocumento = null;
	private java.lang.String importi = null;
	private java.lang.String progetti = null;
	private java.lang.String tipoInvio = null;
	private java.lang.Boolean associabile = null;
	private java.lang.Boolean clonabile = null;
	private java.lang.Boolean eliminabile = null;
	private java.lang.Boolean modificabile = null;
	private java.lang.Boolean associato = null;
	private java.lang.Boolean allegatiPresenti = null;
	private java.lang.Long idProgetto = null;
	
	public java.lang.String getTipologia() {
		return tipologia;
	}
	public void setTipologia(java.lang.String tipologia) {
		this.tipologia = tipologia;
	}
	public java.lang.String getEstremi() {
		return estremi;
	}
	public void setEstremi(java.lang.String estremi) {
		this.estremi = estremi;
	}
	public java.lang.String getFornitore() {
		return fornitore;
	}
	public void setFornitore(java.lang.String fornitore) {
		this.fornitore = fornitore;
	}
	public java.lang.Double getImporto() {
		return importo;
	}
	public void setImporto(java.lang.Double importo) {
		this.importo = importo;
	}
	public java.lang.Double getValidato() {
		return validato;
	}
	public void setValidato(java.lang.Double validato) {
		this.validato = validato;
	}
	public java.lang.String getStato() {
		return stato;
	}
	public void setStato(java.lang.String stato) {
		this.stato = stato;
	}
	public java.lang.String getProgetto() {
		return progetto;
	}
	public void setProgetto(java.lang.String progetto) {
		this.progetto = progetto;
	}
	public java.lang.String getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(java.lang.String idDocumento) {
		this.idDocumento = idDocumento;
	}
	public java.lang.String getImporti() {
		return importi;
	}
	public void setImporti(java.lang.String importi) {
		this.importi = importi;
	}
	public java.lang.String getProgetti() {
		return progetti;
	}
	public void setProgetti(java.lang.String progetti) {
		this.progetti = progetti;
	}
	public java.lang.Boolean getAssociabile() {
		return associabile;
	}
	public void setAssociabile(java.lang.Boolean associabile) {
		this.associabile = associabile;
	}
	public java.lang.Boolean getClonabile() {
		return clonabile;
	}
	public void setClonabile(java.lang.Boolean clonabile) {
		this.clonabile = clonabile;
	}
	public java.lang.Boolean getEliminabile() {
		return eliminabile;
	}
	public void setEliminabile(java.lang.Boolean eliminabile) {
		this.eliminabile = eliminabile;
	}
	public java.lang.Boolean getModificabile() {
		return modificabile;
	}
	public void setModificabile(java.lang.Boolean modificabile) {
		this.modificabile = modificabile;
	}
	public java.lang.Boolean getAssociato() {
		return associato;
	}
	public void setAssociato(java.lang.Boolean associato) {
		this.associato = associato;
	}
	
	public java.lang.Boolean getAllegatiPresenti() {
		return allegatiPresenti;
	}
	public void setAllegatiPresenti(java.lang.Boolean allegatiPresenti) {
		this.allegatiPresenti = allegatiPresenti;
	}
	
	public java.lang.String getTipoInvio() {
		return tipoInvio;
	}
	public void setTipoInvio(java.lang.String tipoInvio) {
		this.tipoInvio = tipoInvio;
	}
	public java.lang.Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(java.lang.Long idProgetto) {
		this.idProgetto = idProgetto;
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
