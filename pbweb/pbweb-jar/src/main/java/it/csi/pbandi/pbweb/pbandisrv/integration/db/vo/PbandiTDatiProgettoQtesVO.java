/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PbandiTDatiProgettoQtesVO extends GenericVO {

  	
	private BigDecimal idDatiQtes;
	private BigDecimal idProgetto;
	private BigDecimal idColonnaQtes;
  	private String estremiAttoApprovazione;
  	private String estremiAttoApprovazioneCcc;
  	private Date dtInserimento;
  	private Date dtAggiornamento;
  	private BigDecimal idUtenteIns;
  	private BigDecimal idUtenteAgg;
	public BigDecimal getIdDatiQtes() {
		return idDatiQtes;
	}
	public void setIdDatiQtes(BigDecimal idDatiQtes) {
		this.idDatiQtes = idDatiQtes;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdColonnaQtes() {
		return idColonnaQtes;
	}
	public void setIdColonnaQtes(BigDecimal idColonnaQtes) {
		this.idColonnaQtes = idColonnaQtes;
	}
	public String getEstremiAttoApprovazione() {
		return estremiAttoApprovazione;
	}
	public void setEstremiAttoApprovazione(String estremiAttoApprovazione) {
		this.estremiAttoApprovazione = estremiAttoApprovazione;
	}
	public String getEstremiAttoApprovazioneCcc() {
		return estremiAttoApprovazioneCcc;
	}
	public void setEstremiAttoApprovazioneCcc(String estremiAttoApprovazioneCcc) {
		this.estremiAttoApprovazioneCcc = estremiAttoApprovazioneCcc;
	}
	public Date getDtInserimento() {
		return dtInserimento;
	}
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	@Override
	public boolean isPKValid() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List getPK() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toString() {
		return "PbandiTDatiProgettoQtesVO [idDatiQtes=" + idDatiQtes + ", idProgetto=" + idProgetto + ", idColonnaQtes="
				+ idColonnaQtes + ", estremiAttoApprovazione=" + estremiAttoApprovazione
				+ ", estremiAttoApprovazioneCcc=" + estremiAttoApprovazioneCcc + ", dtInserimento=" + dtInserimento
				+ ", dtAggiornamento=" + dtAggiornamento + ", idUtenteIns=" + idUtenteIns + ", idUtenteAgg="
				+ idUtenteAgg + "]";
	}


}
