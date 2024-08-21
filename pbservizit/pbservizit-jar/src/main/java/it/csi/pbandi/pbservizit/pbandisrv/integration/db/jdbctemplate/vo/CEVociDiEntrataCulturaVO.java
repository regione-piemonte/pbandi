/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTContoEconomicoVO;

import java.math.BigDecimal;

public class CEVociDiEntrataCulturaVO extends PbandiTContoEconomicoVO {
	private BigDecimal idBando;
	private BigDecimal idVoceDiSpesa;
	private String descVoceDiSpesa;
	private BigDecimal idRigoContoEconomico;
	private BigDecimal massimoImportoAmmissibile;
	private BigDecimal progrOrdinamento;
	private BigDecimal idVoceDiSpesaPadre;
	private BigDecimal idVoceDiEntrata;
	private BigDecimal importoRichiesto;
	private BigDecimal importoAmmesso;
	private BigDecimal importoRendicontato;
	private BigDecimal importoQuietanzato;
	private BigDecimal importoValidato;
	private BigDecimal importoImpegnoVincolante;
	
	// Cultura

	private BigDecimal percQuotaContributo;


	public BigDecimal getIdBando() {
		return idBando;
	}

	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}

	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}

	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}

	public String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}

	public void setDescVoceDiSpesa(String descVoceDiSpesa) {
		this.descVoceDiSpesa = descVoceDiSpesa;
	}

	public BigDecimal getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}

	public void setIdRigoContoEconomico(BigDecimal idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}

	public BigDecimal getProgrOrdinamento() {
		return progrOrdinamento;
	}

	public void setProgrOrdinamento(BigDecimal progrOrdinamento) {
		this.progrOrdinamento = progrOrdinamento;
	}

	public BigDecimal getIdVoceDiSpesaPadre() {
		return idVoceDiSpesaPadre;
	}

	public void setIdVoceDiSpesaPadre(BigDecimal idVoceDiSpesaPadre) {
		this.idVoceDiSpesaPadre = idVoceDiSpesaPadre;
	}

	public void setImportoRichiesto(BigDecimal importoRichiesto) {
		this.importoRichiesto = importoRichiesto;
	}

	public BigDecimal getImportoRichiesto() {
		return importoRichiesto;
	}

	public void setImportoAmmesso(BigDecimal importoAmmesso) {
		this.importoAmmesso = importoAmmesso;
	}

	public BigDecimal getImportoAmmesso() {
		return importoAmmesso;
	}

	public void setImportoRendicontato(BigDecimal importoRendicontato) {
		this.importoRendicontato = importoRendicontato;
	}

	public BigDecimal getImportoRendicontato() {
		return importoRendicontato;
	}

	public void setImportoQuietanzato(BigDecimal importoQuietanzato) {
		this.importoQuietanzato = importoQuietanzato;
	}

	public BigDecimal getImportoQuietanzato() {
		return importoQuietanzato;
	}

	public void setImportoValidato(BigDecimal importoValidato) {
		this.importoValidato = importoValidato;
	}

	public BigDecimal getImportoValidato() {
		return importoValidato;
	}

	public void setMassimoImportoAmmissibile(BigDecimal massimoImportoAmmissibile) {
		this.massimoImportoAmmissibile = massimoImportoAmmissibile;
	}

	public BigDecimal getMassimoImportoAmmissibile() {
		return massimoImportoAmmissibile;
	}

	public void setImportoImpegnoVincolante(BigDecimal importoImpegnoVincolante) {
		this.importoImpegnoVincolante = importoImpegnoVincolante;
	}

	public BigDecimal getImportoImpegnoVincolante() {
		return importoImpegnoVincolante;
	}


	public BigDecimal getPercQuotaContributo() {
		return percQuotaContributo;
	}

	public void setPercQuotaContributo(BigDecimal percQuotaContributo) {
		this.percQuotaContributo = percQuotaContributo;
	}


	public BigDecimal getIdVoceDiEntrata() {
		return idVoceDiEntrata;
	}

	public void setIdVoceDiEntrata(BigDecimal idVoceDiEntrata) {
		this.idVoceDiEntrata = idVoceDiEntrata;
	}


}
