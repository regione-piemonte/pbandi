/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.columnfilter;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTAttoLiquidazioneVO;

public class DettaglioAttoLiquidazioneFilterOutImpegnoVO extends PbandiTAttoLiquidazioneVO {
	private BigDecimal idProgetto;
	private String codiceVisualizzatoProgetto;
	private BigDecimal idBeneficiarioBilancio;
	private String denominazioneBeneficiarioBil;
	private String estremiAtto;
	private String descStatoAtto;
	private String descTipoSoggRitenuta;

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}
	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}
	public BigDecimal getIdBeneficiarioBilancio() {
		return idBeneficiarioBilancio;
	}
	public void setIdBeneficiarioBilancio(BigDecimal idBeneficiarioBilancio) {
		this.idBeneficiarioBilancio = idBeneficiarioBilancio;
	}
	public String getDenominazioneBeneficiarioBil() {
		return denominazioneBeneficiarioBil;
	}
	public void setDenominazioneBeneficiarioBil(String denominazioneBeneficiarioBil) {
		this.denominazioneBeneficiarioBil = denominazioneBeneficiarioBil;
	}
	public String getEstremiAtto() {
		return estremiAtto;
	}
	public void setEstremiAtto(String estremiAtto) {
		this.estremiAtto = estremiAtto;
	}
	public String getDescStatoAtto() {
		return descStatoAtto;
	}
	public void setDescStatoAtto(String descStatoAtto) {
		this.descStatoAtto = descStatoAtto;
	}
	public String getDescTipoSoggRitenuta() {
		return descTipoSoggRitenuta;
	}
	public void setDescTipoSoggRitenuta(String descTipoSoggRitenuta) {
		this.descTipoSoggRitenuta = descTipoSoggRitenuta;
	}
}
