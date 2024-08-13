/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.rettifica;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class VoceDiSpesaRettificaVO extends GenericVO {
	
	private BigDecimal idQuotaParteDocSpesa;
	private BigDecimal idProgetto;
	private BigDecimal idContoEconomico;
	private BigDecimal idDocumentoDiSpesa;
	private BigDecimal progrPagQuotParteDocSp;
	private BigDecimal idVoceDiSpesa;
	private BigDecimal importoValidato;
	
	
	public BigDecimal getIdQuotaParteDocSpesa() {
		return idQuotaParteDocSpesa;
	}
	public void setIdQuotaParteDocSpesa(BigDecimal idQuotaParteDocSpesa) {
		this.idQuotaParteDocSpesa = idQuotaParteDocSpesa;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdContoEconomico() {
		return idContoEconomico;
	}
	public void setIdContoEconomico(BigDecimal idContoEconomico) {
		this.idContoEconomico = idContoEconomico;
	}
	public BigDecimal getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}
	public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}
	public BigDecimal getProgrPagQuotParteDocSp() {
		return progrPagQuotParteDocSp;
	}
	public void setProgrPagQuotParteDocSp(BigDecimal progrPagQuotParteDocSp) {
		this.progrPagQuotParteDocSp = progrPagQuotParteDocSp;
	}
	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}
	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
	public BigDecimal getImportoValidato() {
		return importoValidato;
	}
	public void setImportoValidato(BigDecimal importoValidato) {
		this.importoValidato = importoValidato;
	}

}
