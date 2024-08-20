/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.affidamenti;

public class VarianteAffidamento implements java.io.Serializable {
	private static final long serialVersionUID = 1L;	
	private Long idVariante;
	private Long idAppalto;	
	private Long idTipologiaVariante;
	private String descrizioneTipologiaVariante;
	private Double importo;
	private String note;
	private String dtInserimento;
	private String dtInvioVerificaAffidamento;
	private String flgInvioVerificaAffidamento;

	public Long getIdVariante() {
		return idVariante;
	}

	public void setIdVariante(Long idVariante) {
		this.idVariante = idVariante;
	}

	public Long getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(Long idAppalto) {
		this.idAppalto = idAppalto;
	}

	public Long getIdTipologiaVariante() {
		return idTipologiaVariante;
	}

	public void setIdTipologiaVariante(Long idTipologiaVariante) {
		this.idTipologiaVariante = idTipologiaVariante;
	}

	public String getDescrizioneTipologiaVariante() {
		return descrizioneTipologiaVariante;
	}

	public void setDescrizioneTipologiaVariante(String descrizioneTipologiaVariante) {
		this.descrizioneTipologiaVariante = descrizioneTipologiaVariante;
	}

	public Double getImporto() {
		return importo;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDtInserimento() {
		return dtInserimento;
	}

	public void setDtInserimento(String dtInserimento) {
		this.dtInserimento = dtInserimento;
	}

	public String getDtInvioVerificaAffidamento() {
		return dtInvioVerificaAffidamento;
	}

	public void setDtInvioVerificaAffidamento(String dtInvioVerificaAffidamento) {
		this.dtInvioVerificaAffidamento = dtInvioVerificaAffidamento;
	}

	public String getFlgInvioVerificaAffidamento() {
		return flgInvioVerificaAffidamento;
	}

	public void setFlgInvioVerificaAffidamento(String flgInvioVerificaAffidamento) {
		this.flgInvioVerificaAffidamento = flgInvioVerificaAffidamento;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public VarianteAffidamento() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
