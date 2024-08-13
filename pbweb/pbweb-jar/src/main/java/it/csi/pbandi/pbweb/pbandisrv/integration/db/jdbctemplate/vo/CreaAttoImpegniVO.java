/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class CreaAttoImpegniVO extends GenericVO {
	private BigDecimal idAttoLiquidazione;
	private BigDecimal nroCapitolo;
	private String annoEsercizio;
	private String annoProv; 
	private String nroProv; 
	private String tipoProv; 
	private String direzione; 
	private String annoImp; 
	private String nroImp; 
	private BigDecimal importo; 
	private String cup;
	private String cig; 
	
	public BigDecimal getNroCapitolo() {
		return nroCapitolo;
	}
	public void setNroCapitolo(BigDecimal nroCapitolo) {
		this.nroCapitolo = nroCapitolo;
	}
	public String getAnnoProv() {
		return annoProv;
	}
	public void setAnnoProv(String annoProv) {
		this.annoProv = annoProv;
	}
	public String getNroProv() {
		return nroProv;
	}
	public void setNroProv(String nroProv) {
		this.nroProv = nroProv;
	}
	public String getTipoProv() {
		return tipoProv;
	}
	public void setTipoProv(String tipoProv) {
		this.tipoProv = tipoProv;
	}
	public String getDirezione() {
		return direzione;
	}
	public void setDirezione(String direzione) {
		this.direzione = direzione;
	}

	public String getAnnoImp() {
		return annoImp;
	}
	public String getNroImp() {
		return nroImp;
	}
	public void setNroImp(String nroImp) {
		this.nroImp = nroImp;
	}
	public void setAnnoImp(String annoImp) {
		this.annoImp = annoImp;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public String getCup() {
		return cup;
	}
	public void setCup(String cup) {
		this.cup = cup;
	}
	public String getCig() {
		return cig;
	}
	public void setCig(String cig) {
		this.cig = cig;
	}
	public BigDecimal getIdAttoLiquidazione() {
		return idAttoLiquidazione;
	}
	public void setIdAttoLiquidazione(BigDecimal idAttoLiquidazione) {
		this.idAttoLiquidazione = idAttoLiquidazione;
	}
	public String getAnnoEsercizio() {
		return annoEsercizio;
	}
	public void setAnnoEsercizio(String annoEsercizio) {
		this.annoEsercizio = annoEsercizio;
	}
	
}
