/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class VociDiSpesaPadriFigliContoEconomicoVO extends GenericVO {
	
	private BigDecimal idContoEconomico;
	private BigDecimal idVoceDiSpesa;
	private BigDecimal idVoceDiSpesaPadre;
	private String descVoceDiSpesa;
	private String descVoceDiSpesaPadre;
	private BigDecimal importoAmmessoFinanziamento;
	private BigDecimal idRigoContoEconomico;
	
	
	public BigDecimal getIdContoEconomico() {
		return idContoEconomico;
	}
	public void setIdContoEconomico(BigDecimal idContoEconomico) {
		this.idContoEconomico = idContoEconomico;
	}
	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}
	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
	public BigDecimal getIdVoceDiSpesaPadre() {
		return idVoceDiSpesaPadre;
	}
	public void setIdVoceDiSpesaPadre(BigDecimal idVoceDiSpesaPadre) {
		this.idVoceDiSpesaPadre = idVoceDiSpesaPadre;
	}
	public String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}
	public void setDescVoceDiSpesa(String descVoceDiSpesa) {
		this.descVoceDiSpesa = descVoceDiSpesa;
	}
	public String getDescVoceDiSpesaPadre() {
		return descVoceDiSpesaPadre;
	}
	public void setDescVoceDiSpesaPadre(String descVoceDiSpesaPadre) {
		this.descVoceDiSpesaPadre = descVoceDiSpesaPadre;
	}
	public BigDecimal getImportoAmmessoFinanziamento() {
		return importoAmmessoFinanziamento;
	}
	public void setImportoAmmessoFinanziamento(
			BigDecimal importoAmmessoFinanziamento) {
		this.importoAmmessoFinanziamento = importoAmmessoFinanziamento;
	}
	public BigDecimal getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}
	public void setIdRigoContoEconomico(BigDecimal idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}


}
