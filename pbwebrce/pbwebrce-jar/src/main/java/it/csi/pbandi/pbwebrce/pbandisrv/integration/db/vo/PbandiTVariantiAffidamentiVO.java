/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbwebrce.pbandiutil.common.DateUtil;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class PbandiTVariantiAffidamentiVO extends GenericVO{
	
	private BigDecimal idVariante;
	private Double importo;
	private BigDecimal idTipologiaVariante;
	private BigDecimal idAppalto;
	private String note;
	private String flgInvioVerificaAffidamento;
	private Date dtInvioVerificaAffidamento;
	private Date dtInserimento;
	
	public PbandiTVariantiAffidamentiVO(){}
	
	public PbandiTVariantiAffidamentiVO(BigDecimal idVariante){
		this.idVariante = idVariante;
	}

	public PbandiTVariantiAffidamentiVO(BigDecimal idVariante, Double importo,
			BigDecimal idTipologiaVariante, BigDecimal idAppalto, String note,
			String flgInvioVerificaAffidamento, Date dtInvioVerificaAffidamento) {
		super();
		this.idVariante = idVariante;
		this.importo = importo;
		this.idTipologiaVariante = idTipologiaVariante;
		this.idAppalto = idAppalto;
		this.note = note;
		this.dtInvioVerificaAffidamento = dtInvioVerificaAffidamento;
		this.flgInvioVerificaAffidamento = flgInvioVerificaAffidamento;
	}

	public BigDecimal getIdVariante() {
		return idVariante;
	}

	public void setIdVariante(BigDecimal idVariante) {
		this.idVariante = idVariante;
	}

	public Double getImporto() {
		return importo;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}

	public BigDecimal getIdTipologiaVariante() {
		return idTipologiaVariante;
	}

	public void setIdTipologiaVariante(BigDecimal idTipologiaVariante) {
		this.idTipologiaVariante = idTipologiaVariante;
	}

	public BigDecimal getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(BigDecimal idAppalto) {
		this.idAppalto = idAppalto;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getFlgInvioVerificaAffidamento() {
		return flgInvioVerificaAffidamento;
	}


	public void setFlgInvioVerificaAffidamento(String flgInvioVerificaAffidamento) {
		this.flgInvioVerificaAffidamento = flgInvioVerificaAffidamento;
	}


	public Date getDtInvioVerificaAffidamento() {
		return dtInvioVerificaAffidamento;
	}


	public void setDtInvioVerificaAffidamento(Date dtInvioVerificaAffidamento) {
		this.dtInvioVerificaAffidamento = dtInvioVerificaAffidamento;
	}

	public Date getDtInserimento() {
		return dtInserimento;
	}

	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}

	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idAppalto != null && idTipologiaVariante != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	
	@Override
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idVariante != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idVariante");
		
	    return pk;
	}


	@Override
	public String toString() {
		return "PbandiTVariantiAffidamentiVO [idAppalto=" + idAppalto
				+ ", idTipologiaVariante=" + idTipologiaVariante
				+ ", idVariante=" + idVariante + ", importo=" + importo
				+ ", flgInvioVerificaAffidamento=" + flgInvioVerificaAffidamento
				+ ", dtInvioVerificaAffidamento=" + DateUtil.getDate(dtInvioVerificaAffidamento)
				+ ", note=" + note + "]";
	}

}
