/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTContoEconomicoVO;

public class ContoEconomicoConRighiEVociVO extends PbandiTContoEconomicoVO {
	private BigDecimal idBando;
	private BigDecimal idVoceDiSpesa;
	private String descVoceDiSpesa;
	private BigDecimal idRigoContoEconomico;
	private BigDecimal massimoImportoAmmissibile;
	private BigDecimal progrOrdinamento;
	private BigDecimal idVoceDiSpesaPadre;
	private BigDecimal importoRichiesto;
	private BigDecimal importoAmmesso;
	private BigDecimal importoRendicontato;
	private BigDecimal importoQuietanzato;
	private BigDecimal importoValidato;
	private BigDecimal importoImpegnoVincolante;
	
	// Cultura
	private BigDecimal idTipologiaVoceDiSpesa;
	private String descTipologiaVoceDiSpesa;
	private BigDecimal percSpGenFunz;
	private String flagEdit;
	private String completamento;
	private BigDecimal percQuotaContributo;
	private BigDecimal importoSpesaPreventivata;

	public String getCompletamento() {
		return completamento;
	}

	public void setCompletamento(String completamento) {
		this.completamento = completamento;
	}

	public String getFlagEdit() {
		return flagEdit;
	}

	public void setFlagEdit(String flagEdit) {
		this.flagEdit = flagEdit;
	}

	public BigDecimal getPercSpGenFunz() {
		return percSpGenFunz;
	}

	public void setPercSpGenFunz(BigDecimal percSpGenFunz) {
		this.percSpGenFunz = percSpGenFunz;
	}

	public BigDecimal getIdTipologiaVoceDiSpesa() {
		return idTipologiaVoceDiSpesa;
	}

	public void setIdTipologiaVoceDiSpesa(BigDecimal idTipologiaVoceDiSpesa) {
		this.idTipologiaVoceDiSpesa = idTipologiaVoceDiSpesa;
	}

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

	public String getDescTipologiaVoceDiSpesa() {
		return descTipologiaVoceDiSpesa;
	}

	public void setDescTipologiaVoceDiSpesa(String descTipologiaVoceDiSpesa) {
		this.descTipologiaVoceDiSpesa = descTipologiaVoceDiSpesa;
	}

	public BigDecimal getPercQuotaContributo() {
		return percQuotaContributo;
	}

	public void setPercQuotaContributo(BigDecimal percQuotaContributo) {
		this.percQuotaContributo = percQuotaContributo;
	}


	public BigDecimal getImportoSpesaPreventivata() {
		return importoSpesaPreventivata;
	}

	public void setImportoSpesaPreventivata(BigDecimal importoSpesaPreventivata) {
		this.importoSpesaPreventivata = importoSpesaPreventivata;
	}
}
