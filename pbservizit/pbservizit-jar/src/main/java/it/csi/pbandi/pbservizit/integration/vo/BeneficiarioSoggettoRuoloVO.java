/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.vo;

import java.math.BigDecimal;

public class BeneficiarioSoggettoRuoloVO {

	private String codiceFiscaleSoggetto;
	private BigDecimal idSoggetto;
	private BigDecimal idTipoAnagrafica;
	private BigDecimal idProgetto;
	private String descBreveTipoAnagrafica;
	private BigDecimal idSoggettoBeneficiario;
	private BigDecimal progrBandoLineaIntervento;
	private String codiceFiscaleBeneficiario;
	private String denominazioneBeneficiario;
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("BeneficiarioSoggettoRuoloVO [codiceFiscaleSoggetto=" + codiceFiscaleSoggetto + ", idSoggetto="+ idSoggetto );
		sb.append( ", idTipoAnagrafica=" + idTipoAnagrafica + ", idProgetto=" + idProgetto + ", descBreveTipoAnagrafica=" + descBreveTipoAnagrafica );
		sb.append( ", idSoggettoBeneficiario="	+ idSoggettoBeneficiario + ", progrBandoLineaIntervento=" + progrBandoLineaIntervento );
		sb.append( ", codiceFiscaleBeneficiario=" + codiceFiscaleBeneficiario + ", denominazioneBeneficiario="	+ denominazioneBeneficiario + "]");
		return sb.toString();
		 
	}
	
	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}
	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public BigDecimal getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}
	public void setIdTipoAnagrafica(BigDecimal idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getDescBreveTipoAnagrafica() {
		return descBreveTipoAnagrafica;
	}
	public void setDescBreveTipoAnagrafica(String descBreveTipoAnagrafica) {
		this.descBreveTipoAnagrafica = descBreveTipoAnagrafica;
	}
	public BigDecimal getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	public void setIdSoggettoBeneficiario(BigDecimal idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public String getCodiceFiscaleBeneficiario() {
		return codiceFiscaleBeneficiario;
	}
	public void setCodiceFiscaleBeneficiario(String codiceFiscaleBeneficiario) {
		this.codiceFiscaleBeneficiario = codiceFiscaleBeneficiario;
	}
	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}
	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}
	
}
