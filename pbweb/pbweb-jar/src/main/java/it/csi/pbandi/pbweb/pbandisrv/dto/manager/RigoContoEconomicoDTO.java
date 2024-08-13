/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.manager;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class RigoContoEconomicoDTO {
	private BigDecimal idContoEconomico;
	private BigDecimal idRigoContoEconomico;
	private BigDecimal massimoImportoAmmissibile;
	private BigDecimal progrOrdinamento;
	private BigDecimal idVoceDiSpesa;
	private BigDecimal idVoceDiSpesaPadre;
	private String descVoceDiSpesa;
	private List<RigoContoEconomicoDTO> figli;
	private BigDecimal importoRichiesto;
	private BigDecimal importoAmmesso;
	private BigDecimal importoRendicontato;
	private BigDecimal importoQuietanzato;
	private BigDecimal importoValidato;
	private boolean voceAssociataARigo = true;
	private Date dtFineValidita;
	private Date dtInizioValidita;
	
	// Cultura
	private BigDecimal idTipologiaVoceDiSpesa;

	public BigDecimal getIdTipologiaVoceDiSpesa() {
		return idTipologiaVoceDiSpesa;
	}

	public void setIdTipologiaVoceDiSpesa(BigDecimal idTipologiaVoceDiSpesa) {
		this.idTipologiaVoceDiSpesa = idTipologiaVoceDiSpesa;
	}

	public BigDecimal getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}

	public void setIdRigoContoEconomico(BigDecimal idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
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

	public void setIdContoEconomico(BigDecimal idContoEconomico) {
		this.idContoEconomico = idContoEconomico;
	}

	public BigDecimal getIdContoEconomico() {
		return idContoEconomico;
	}

	public void setFigli(List<RigoContoEconomicoDTO> figli) {
		this.figli = figli;
	}

	public List<RigoContoEconomicoDTO> getFigli() {
		return figli;
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

	public void setDescVoceDiSpesa(String descVoceDiSpesa) {
		this.descVoceDiSpesa = descVoceDiSpesa;
	}

	public String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
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

	public void setProgrOrdinamento(BigDecimal progrOrdinamento) {
		this.progrOrdinamento = progrOrdinamento;
	}

	public BigDecimal getProgrOrdinamento() {
		return progrOrdinamento;
	}

	public void setMassimoImportoAmmissibile(
			BigDecimal massimoImportoAmmissibile) {
		this.massimoImportoAmmissibile = massimoImportoAmmissibile;
	}

	public BigDecimal getMassimoImportoAmmissibile() {
		return massimoImportoAmmissibile;
	}

	public void setVoceAssociataARigo(boolean voceAssociataARigo) {
		this.voceAssociataARigo = voceAssociataARigo;
	}

	public boolean isVoceAssociataARigo() {
		return voceAssociataARigo;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}

	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}

	@Override
	public String toString() {
		return "RigoContoEconomicoDTO [idContoEconomico=" + idContoEconomico + ", idRigoContoEconomico="
				+ idRigoContoEconomico + ", massimoImportoAmmissibile=" + massimoImportoAmmissibile
				+ ", progrOrdinamento=" + progrOrdinamento + ", idVoceDiSpesa=" + idVoceDiSpesa
				+ ", idVoceDiSpesaPadre=" + idVoceDiSpesaPadre + ", descVoceDiSpesa=" + descVoceDiSpesa
				+ ", importoRichiesto=" + importoRichiesto + ", importoAmmesso=" + importoAmmesso
				+ ", importoRendicontato=" + importoRendicontato + ", importoQuietanzato=" + importoQuietanzato
				+ ", importoValidato=" + importoValidato + ", voceAssociataARigo=" + voceAssociataARigo
				+ ", dtFineValidita=" + dtFineValidita + ", dtInizioValidita=" + dtInizioValidita
				+ ", idTipologiaVoceDiSpesa=" + idTipologiaVoceDiSpesa + "]";
	}
}
