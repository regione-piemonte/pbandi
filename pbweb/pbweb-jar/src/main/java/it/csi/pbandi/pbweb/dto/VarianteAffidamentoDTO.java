/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto;

public class VarianteAffidamentoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Long idVariante = null;
	private java.lang.Long idTipologiaVariante = null;
	private java.lang.Long idAppalto = null;
	private java.lang.Double importo = null;
	private java.lang.String note = null;
	private java.lang.String descrizioneTipologiaVariante = null;
	private java.util.Date dtInserimento = null;
	private java.util.Date dtInvioVerificaAffidamento = null;
	private java.lang.String flgInvioVerificaAffidamento = null;
	public java.lang.Long getIdVariante() {
		return idVariante;
	}
	public void setIdVariante(java.lang.Long idVariante) {
		this.idVariante = idVariante;
	}
	public java.lang.Long getIdTipologiaVariante() {
		return idTipologiaVariante;
	}
	public void setIdTipologiaVariante(java.lang.Long idTipologiaVariante) {
		this.idTipologiaVariante = idTipologiaVariante;
	}
	public java.lang.Long getIdAppalto() {
		return idAppalto;
	}
	public void setIdAppalto(java.lang.Long idAppalto) {
		this.idAppalto = idAppalto;
	}
	public java.lang.Double getImporto() {
		return importo;
	}
	public void setImporto(java.lang.Double importo) {
		this.importo = importo;
	}
	public java.lang.String getNote() {
		return note;
	}
	public void setNote(java.lang.String note) {
		this.note = note;
	}
	public java.lang.String getDescrizioneTipologiaVariante() {
		return descrizioneTipologiaVariante;
	}
	public void setDescrizioneTipologiaVariante(java.lang.String descrizioneTipologiaVariante) {
		this.descrizioneTipologiaVariante = descrizioneTipologiaVariante;
	}
	public java.util.Date getDtInserimento() {
		return dtInserimento;
	}
	public void setDtInserimento(java.util.Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	public java.util.Date getDtInvioVerificaAffidamento() {
		return dtInvioVerificaAffidamento;
	}
	public void setDtInvioVerificaAffidamento(java.util.Date dtInvioVerificaAffidamento) {
		this.dtInvioVerificaAffidamento = dtInvioVerificaAffidamento;
	}
	public java.lang.String getFlgInvioVerificaAffidamento() {
		return flgInvioVerificaAffidamento;
	}
	public void setFlgInvioVerificaAffidamento(java.lang.String flgInvioVerificaAffidamento) {
		this.flgInvioVerificaAffidamento = flgInvioVerificaAffidamento;
	}

}
