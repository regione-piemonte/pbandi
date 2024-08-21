/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.revoca;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;


public class QuotaParteVoceSpesaVO extends GenericVO {
	
	private BigDecimal idContoEconomico;
	private BigDecimal idProgetto;
	private BigDecimal idVoceDiSpesa;
	private BigDecimal idPagamento;
	private BigDecimal idQuotaParteDocSpesa;
	private BigDecimal progrPagQuotParteDocSp;
	private String descVoceDiSpesa;
	private BigDecimal importoValidato;
	//private BigDecimal idStatoValidazioneSpesa;
	//private String descStatoValidazioneSpesa;
	
	public BigDecimal getIdContoEconomico() {
		return idContoEconomico;
	}
	public void setIdContoEconomico(BigDecimal idContoEconomico) {
		this.idContoEconomico = idContoEconomico;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}
	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
	public BigDecimal getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(BigDecimal idPagamento) {
		this.idPagamento = idPagamento;
	}
	
	public BigDecimal getProgrPagQuotParteDocSp() {
		return progrPagQuotParteDocSp;
	}
	public void setProgrPagQuotParteDocSp(BigDecimal progrPagQuotParteDocSp) {
		this.progrPagQuotParteDocSp = progrPagQuotParteDocSp;
	}
	
	public BigDecimal getImportoValidato() {
		return importoValidato;
	}
	public void setImportoValidato(BigDecimal importoValidato) {
		this.importoValidato = importoValidato;
	}
	/*public BigDecimal getIdStatoValidazioneSpesa() {
		return idStatoValidazioneSpesa;
	}
	public void setIdStatoValidazioneSpesa(BigDecimal idStatoValidazioneSpesa) {
		this.idStatoValidazioneSpesa = idStatoValidazioneSpesa;
	}*/
	public String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}
	public void setDescVoceDiSpesa(String descVoceDiSpesa) {
		this.descVoceDiSpesa = descVoceDiSpesa;
	}
	/*public String getDescStatoValidazioneSpesa() {
		return descStatoValidazioneSpesa;
	}
	public void setDescStatoValidazioneSpesa(String descStatoValidazioneSpesa) {
		this.descStatoValidazioneSpesa = descStatoValidazioneSpesa;
	}*/
	public void setIdQuotaParteDocSpesa(BigDecimal idQuotaParteDocSpesa) {
		this.idQuotaParteDocSpesa = idQuotaParteDocSpesa;
	}
	public BigDecimal getIdQuotaParteDocSpesa() {
		return idQuotaParteDocSpesa;
	}
	

}
