/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class VoceDiSpesaInterventoVO extends GenericVO {
	
 	private BigDecimal idVoceDiSpesa;
  	
  	private Date dtFineValidita;
  	
  	private String codTipoVoceDiSpesa;
  	
  	private BigDecimal idVoceDiSpesaMonit;
  	
  	private Date dtInizioValidita;
  	
  	private String descVoceDiSpesa;
  	
  	private BigDecimal idVoceDiSpesaPadre;
  	
  	private BigDecimal idBando;

	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}

	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public String getCodTipoVoceDiSpesa() {
		return codTipoVoceDiSpesa;
	}

	public void setCodTipoVoceDiSpesa(String codTipoVoceDiSpesa) {
		this.codTipoVoceDiSpesa = codTipoVoceDiSpesa;
	}

	public BigDecimal getIdVoceDiSpesaMonit() {
		return idVoceDiSpesaMonit;
	}

	public void setIdVoceDiSpesaMonit(BigDecimal idVoceDiSpesaMonit) {
		this.idVoceDiSpesaMonit = idVoceDiSpesaMonit;
	}

	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}

	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}

	public String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}

	public void setDescVoceDiSpesa(String descVoceDiSpesa) {
		this.descVoceDiSpesa = descVoceDiSpesa;
	}

	public BigDecimal getIdVoceDiSpesaPadre() {
		return idVoceDiSpesaPadre;
	}

	public void setIdVoceDiSpesaPadre(BigDecimal idVoceDiSpesaPadre) {
		this.idVoceDiSpesaPadre = idVoceDiSpesaPadre;
	}

	public BigDecimal getIdBando() {
		return idBando;
	}

	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}
	
}
