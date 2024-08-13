/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa;

public class EsitoOperazioneInviaDichiarazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Boolean esito = null;
	private java.lang.String msg = null;
	
	private java.lang.String nomeFileDichiarazioneSpesa = null;
	private java.lang.Long idDichiarazioneSpesa = null;
	private java.lang.Long idDocumentoIndex = null;
	
	private java.lang.String nomeFileDichiarazioneSpesaPiuGreen = null;
	private java.lang.Long idDichiarazioneSpesaPiuGreen = null;
	private java.lang.Long idDocumentoIndexPiuGreen = null;
	
	public Long getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(Long idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	public java.lang.Boolean getEsito() {
		return esito;
	}
	public void setEsito(java.lang.Boolean esito) {
		this.esito = esito;
	}
	public java.lang.String getMsg() {
		return msg;
	}
	public void setMsg(java.lang.String msg) {
		this.msg = msg;
	}
	public java.lang.String getNomeFileDichiarazioneSpesa() {
		return nomeFileDichiarazioneSpesa;
	}
	public void setNomeFileDichiarazioneSpesa(java.lang.String nomeFileDichiarazioneSpesa) {
		this.nomeFileDichiarazioneSpesa = nomeFileDichiarazioneSpesa;
	}
	public Long getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}
	public void setIdDichiarazioneSpesa(Long idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
	public java.lang.String getNomeFileDichiarazioneSpesaPiuGreen() {
		return nomeFileDichiarazioneSpesaPiuGreen;
	}
	public void setNomeFileDichiarazioneSpesaPiuGreen(java.lang.String nomeFileDichiarazioneSpesaPiuGreen) {
		this.nomeFileDichiarazioneSpesaPiuGreen = nomeFileDichiarazioneSpesaPiuGreen;
	}
	public Long getIdDichiarazioneSpesaPiuGreen() {
		return idDichiarazioneSpesaPiuGreen;
	}
	public void setIdDichiarazioneSpesaPiuGreen(Long idDichiarazioneSpesaPiuGreen) {
		this.idDichiarazioneSpesaPiuGreen = idDichiarazioneSpesaPiuGreen;
	}
	public Long getIdDocumentoIndexPiuGreen() {
		return idDocumentoIndexPiuGreen;
	}
	public void setIdDocumentoIndexPiuGreen(Long idDocumentoIndexPiuGreen) {
		this.idDocumentoIndexPiuGreen = idDocumentoIndexPiuGreen;
	}	

}
