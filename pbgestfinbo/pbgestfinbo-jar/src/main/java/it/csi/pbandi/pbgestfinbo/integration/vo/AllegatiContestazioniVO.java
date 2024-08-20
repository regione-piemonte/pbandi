/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;


public class AllegatiContestazioniVO {
	
	
	private String nomeFile;
	private String flagEntita;
	private int idDocumentoIndex;
	private int idFileEntita;
	private String codTipoDoc;
	
	
	
	
	public String getCodTipoDoc() {
		return codTipoDoc;
	}
	public void setCodTipoDoc(String codTipoDoc) {
		this.codTipoDoc = codTipoDoc;
	}
	public int getIdFileEntita() {
		return idFileEntita;
	}
	public void setIdFileEntita(int idFileEntita) {
		this.idFileEntita = idFileEntita;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	
	public String getFlagEntita() {
		return flagEntita;
	}
	public void setFlagEntita(String flagEntita) {
		this.flagEntita = flagEntita;
	}
	
	public int getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(int idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	
	
}