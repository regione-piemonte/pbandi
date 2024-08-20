/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbwebrce.util.BeanUtil;


public class DocumentoAllegatoDTO implements java.io.Serializable {
	
	static final long serialVersionUID = 1;

	private java.lang.String codiceVisualizzatoProgetto = null;
	private boolean hasDocumentoDiSpesaProtocollo = false;
	private java.lang.Long id = null;
	private java.lang.Long idParent = null;
	private boolean disassociabile = false;
	private boolean documentoDiSpesaElettronico = false;
	private java.lang.Long idProgetto = null;
	private java.lang.String nome = null;
	private java.lang.String protocollo = null;
	private java.lang.Long sizeFile = null;
	private java.lang.String flagEntita = null;
	private java.util.Date dtAssociazione = null;		// Jira PBANDI-2890.
	private java.lang.String idIntegrazioneSpesa = null;
	
	

	public java.lang.String getIdIntegrazioneSpesa() {
		return idIntegrazioneSpesa;
	}

	public void setIdIntegrazioneSpesa(java.lang.String idIntegrazioneSpesa) {
		this.idIntegrazioneSpesa = idIntegrazioneSpesa;
	}

	public java.util.Date getDtAssociazione() {
		return dtAssociazione;
	}

	public void setDtAssociazione(java.util.Date dtAssociazione) {
		this.dtAssociazione = dtAssociazione;
	}

	public java.lang.String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}

	public void setCodiceVisualizzatoProgetto(java.lang.String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}

	public boolean isHasDocumentoDiSpesaProtocollo() {
		return hasDocumentoDiSpesaProtocollo;
	}

	public void setHasDocumentoDiSpesaProtocollo(boolean hasDocumentoDiSpesaProtocollo) {
		this.hasDocumentoDiSpesaProtocollo = hasDocumentoDiSpesaProtocollo;
	}

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getIdParent() {
		return idParent;
	}

	public void setIdParent(java.lang.Long idParent) {
		this.idParent = idParent;
	}

	public boolean isDisassociabile() {
		return disassociabile;
	}

	public void setDisassociabile(boolean disassociabile) {
		this.disassociabile = disassociabile;
	}

	public boolean isDocumentoDiSpesaElettronico() {
		return documentoDiSpesaElettronico;
	}

	public void setDocumentoDiSpesaElettronico(boolean documentoDiSpesaElettronico) {
		this.documentoDiSpesaElettronico = documentoDiSpesaElettronico;
	}

	public java.lang.Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(java.lang.Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public java.lang.String getNome() {
		return nome;
	}

	public void setNome(java.lang.String nome) {
		this.nome = nome;
	}

	public java.lang.String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(java.lang.String protocollo) {
		this.protocollo = protocollo;
	}

	public java.lang.Long getSizeFile() {
		return sizeFile;
	}

	public void setSizeFile(java.lang.Long sizeFile) {
		this.sizeFile = sizeFile;
	}

	public java.lang.String getFlagEntita() {
		return flagEntita;
	}

	public void setFlagEntita(java.lang.String flagEntita) {
		this.flagEntita = flagEntita;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nDocumentoAllegatoDTO: ");
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
