/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class DatiPagamentoVO extends GenericVO{
	
	private BigDecimal idAttoLiquidazione;
	private String progModPag;
	private String codAccre;
	private String abi;
	private String cab;
	private String nroCc;
	private String cin;
	private String iban;
	private String bic;
	private String ragSocAgg;
	private String viaSede;
	private String capSede;
	private String comuneSede;
	private String comuneEstero;
	private String provSede;
	private String mailModPag;

	
	public BigDecimal getIdAttoLiquidazione() {
		return idAttoLiquidazione;
	}
	public void setIdAttoLiquidazione(BigDecimal idAttoLiquidazione) {
		this.idAttoLiquidazione = idAttoLiquidazione;
	}
	public String getProgModPag() {
		return progModPag;
	}
	public void setProgModPag(String progModPag) {
		this.progModPag = progModPag;
	}
	public String getCodAccre() {
		return codAccre;
	}
	public void setCodAccre(String codAccre) {
		this.codAccre = codAccre;
	}
	public String getAbi() {
		return abi;
	}
	public void setAbi(String abi) {
		this.abi = abi;
	}
	public String getCab() {
		return cab;
	}
	public void setCab(String cab) {
		this.cab = cab;
	}
	public String getNroCc() {
		return nroCc;
	}
	public void setNroCc(String nroCc) {
		this.nroCc = nroCc;
	}
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getBic() {
		return bic;
	}
	public void setBic(String bic) {
		this.bic = bic;
	}
	public String getRagSocAgg() {
		return ragSocAgg;
	}
	public void setRagSocAgg(String ragSocAgg) {
		this.ragSocAgg = ragSocAgg;
	}
	public String getViaSede() {
		return viaSede;
	}
	public void setViaSede(String viaSede) {
		this.viaSede = viaSede;
	}
	public String getCapSede() {
		return capSede;
	}
	public void setCapSede(String capSede) {
		this.capSede = capSede;
	}
	public String getComuneSede() {
		return comuneSede;
	}
	public void setComuneSede(String comuneSede) {
		this.comuneSede = comuneSede;
	}
	public String getProvSede() {
		return provSede;
	}
	public void setProvSede(String provSede) {
		this.provSede = provSede;
	}
	public String getMailModPag() {
		return mailModPag;
	}
	public void setMailModPag(String mailModPag) {
		this.mailModPag = mailModPag;
	}
	public String getComuneEstero() {
		return comuneEstero;
	}
	public void setComuneEstero(String comuneEstero) {
		this.comuneEstero = comuneEstero;
	}
	
	

}
