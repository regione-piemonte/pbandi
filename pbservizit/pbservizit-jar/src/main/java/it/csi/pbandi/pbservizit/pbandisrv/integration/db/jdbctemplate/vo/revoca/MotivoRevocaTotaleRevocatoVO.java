/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.revoca;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;


public class MotivoRevocaTotaleRevocatoVO extends GenericVO {
	
	private BigDecimal idModalitaAgevolazione;
	private BigDecimal idProgetto;
	private BigDecimal idMotivoRevoca;
	private String descMotivoRevoca;
	private Date dtUltimaRevoca;
	private BigDecimal totaleImportoRevocato;
	private String estremiUltimaRevoca;
	public BigDecimal getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	public void setIdModalitaAgevolazione(BigDecimal idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdMotivoRevoca() {
		return idMotivoRevoca;
	}
	public void setIdMotivoRevoca(BigDecimal idMotivoRevoca) {
		this.idMotivoRevoca = idMotivoRevoca;
	}
	public String getDescMotivoRevoca() {
		return descMotivoRevoca;
	}
	public void setDescMotivoRevoca(String descMotivoRevoca) {
		this.descMotivoRevoca = descMotivoRevoca;
	}
	public Date getDtUltimaRevoca() {
		return dtUltimaRevoca;
	}
	public void setDtUltimaRevoca(Date dtUltimaRevoca) {
		this.dtUltimaRevoca = dtUltimaRevoca;
	}
	public BigDecimal getTotaleImportoRevocato() {
		return totaleImportoRevocato;
	}
	public void setTotaleImportoRevocato(BigDecimal totaleImportoRevocato) {
		this.totaleImportoRevocato = totaleImportoRevocato;
	}
	public String getEstremiUltimaRevoca() {
		return estremiUltimaRevoca;
	}
	public void setEstremiUltimaRevoca(String estremiUltimaRevoca) {
		this.estremiUltimaRevoca = estremiUltimaRevoca;
	}
	
}
