/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo;

import java.util.Date;

public class RitenutaAttoVO {

	////////////////////fields corrispondenti su tabella ATTI_LIQUID relativi alla ritenuta atto	
	private String tipoSoggetto;  //TIPO_SOGGETTO (1)
	private String tipoRitenuta;  //TIPO_RITENUTA (1)
	private Double irpnonsogg;  //IRPNONSOGG (15,2)
	private Double aliqIrpef;  //ALIQ_IRPEF (5,2)
	private Integer datoInps;  //DATO_INPS (2)
	private String inpsAltraCassa;  //INPSALTRACASSA (1)
	private String codAltraCassa;  //COD_ALTRACASSA (3)
	private String codAttivita;  //COD_ATTIVITA (2)
	private Date inpsAl;  //INPSAL
	private Date inpsDal;  //INPSDAL
	private String rischioInail;  //RISCHIO_INAIL (2)
	
	//inizio accessor con fields non corrispondenti di tabella ATTI_LIQUID:
	public Double getIrpNonSoggette() {
		return getIrpnonsogg();
	}
	public void setIrpNonSoggette(Double irpNonSoggette) {
		this.setIrpnonsogg(irpNonSoggette);
	}
	//fine accessor con fields non corrispondenti di tabella ATTI_LIQUID:

	
	public String getTipoSoggetto() {
		return tipoSoggetto;
	}


	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}


	public String getTipoRitenuta() {
		return tipoRitenuta;
	}


	public void setTipoRitenuta(String tipoRitenuta) {
		this.tipoRitenuta = tipoRitenuta;
	}


	private Double getIrpnonsogg() {
		return irpnonsogg;
	}


	private void setIrpnonsogg(Double irpnonsogg) {
		this.irpnonsogg = irpnonsogg;
	}


	public Double getAliqIrpef() {
		return aliqIrpef;
	}


	public void setAliqIrpef(Double aliqIrpef) {
		this.aliqIrpef = aliqIrpef;
	}


	public Integer getDatoInps() {
		return datoInps;
	}


	public void setDatoInps(Integer datoInps) {
		this.datoInps = datoInps;
	}


	public String getInpsAltraCassa() {
		return inpsAltraCassa;
	}


	public void setInpsAltraCassa(String inpsAltraCassa) {
		this.inpsAltraCassa = inpsAltraCassa;
	}


	public String getCodAltraCassa() {
		return codAltraCassa;
	}


	public void setCodAltraCassa(String codAltraCassa) {
		this.codAltraCassa = codAltraCassa;
	}


	public String getCodAttivita() {
		return codAttivita;
	}


	public void setCodAttivita(String codAttivita) {
		this.codAttivita = codAttivita;
	}


	public Date getInpsAl() {
		return inpsAl;
	}


	public void setInpsAl(Date inpsAl) {
		this.inpsAl = inpsAl;
	}


	public Date getInpsDal() {
		return inpsDal;
	}


	public void setInpsDal(Date inpsDal) {
		this.inpsDal = inpsDal;
	}


	public String getRischioInail() {
		return rischioInail;
	}


	public void setRischioInail(String rischioInail) {
		this.rischioInail = rischioInail;
	}
	
}
