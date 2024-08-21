/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo;

import java.util.Date;

public class DettaglioConsultazioneAttoVO {

	//dati degli impegni relativi agli atti di liquidazione 
	//reperiti da varie tabelle (liquidazioni, imp_liq, cap_uscita, impegni, subimp, mandati)
	private Integer annoEser;
	private Integer nroCapitolo;
	private Integer nroArticolo;
	private String tipoFondo;
	private String annoImp;
	private Integer nroImp;
	private String annoProvv;
	private String nroProv;
	private String tipoProv;
	private String direzione;
	private String trasfTipo;
	private String trasfVoce;
	private String subImpegno;
	private Integer nLiq;
	private Integer nLiqPrec;
	private String annoBilRif;
	private Double importo;
	private Date dataAgg;
	private Integer nroMand;
	private Double importoMandLordo;
	private Double importoRitenute;
	private Double importoMandNetto;
	private Date dataQuiet;
	private Double importoQuiet;
	private String cupLiq;
	private String cigLiq;
	private String cupMand;
	private String cigMand;
	private String flagPign;
	
	public Integer getAnnoEser() {
		return annoEser;
	}
	public void setAnnoEser(Integer annoEser) {
		this.annoEser = annoEser;
	}
	public Integer getNroCapitolo() {
		return nroCapitolo;
	}
	public void setNroCapitolo(Integer nroCapitolo) {
		this.nroCapitolo = nroCapitolo;
	}
	public Integer getNroArticolo() {
		return nroArticolo;
	}
	public void setNroArticolo(Integer nroArticolo) {
		this.nroArticolo = nroArticolo;
	}
	public String getTipoFondo() {
		return tipoFondo;
	}
	public void setTipoFondo(String tipoFondo) {
		this.tipoFondo = tipoFondo;
	}
	public String getAnnoImp() {
		return annoImp;
	}
	public void setAnnoImp(String annoImp) {
		this.annoImp = annoImp;
	}
	public Integer getNroImp() {
		return nroImp;
	}
	public void setNroImp(Integer nroImp) {
		this.nroImp = nroImp;
	}
	public String getAnnoProvv() {
		return annoProvv;
	}
	public void setAnnoProvv(String annoProvv) {
		this.annoProvv = annoProvv;
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
	public String getTrasfTipo() {
		return trasfTipo;
	}
	public void setTrasfTipo(String trasfTipo) {
		this.trasfTipo = trasfTipo;
	}
	public String getTrasfVoce() {
		return trasfVoce;
	}
	public void setTrasfVoce(String trasfVoce) {
		this.trasfVoce = trasfVoce;
	}
	public String getSubImpegno() {
		return subImpegno;
	}
	public void setSubImpegno(String subImpegno) {
		this.subImpegno = subImpegno;
	}
	public Integer getNLiq() {
		return nLiq;
	}
	public void setNLiq(Integer liq) {
		nLiq = liq;
	}
	public Integer getNLiqPrec() {
		return nLiqPrec;
	}
	public void setNLiqPrec(Integer liqPrec) {
		nLiqPrec = liqPrec;
	}
	public String getAnnoBilRif() {
		return annoBilRif;
	}
	public void setAnnoBilRif(String annoBilRif) {
		this.annoBilRif = annoBilRif;
	}
	public Double getImporto() {
		return importo;
	}
	public void setImporto(Double importo) {
		this.importo = importo;
	}
	public Date getDataAgg() {
		return dataAgg;
	}
	public void setDataAgg(Date dataAgg) {
		this.dataAgg = dataAgg;
	}
	public Integer getNroMand() {
		return nroMand;
	}
	public void setNroMand(Integer nroMand) {
		this.nroMand = nroMand;
	}
	public Double getImportoMandLordo() {
		return importoMandLordo;
	}
	public void setImportoMandLordo(Double importoMandLordo) {
		this.importoMandLordo = importoMandLordo;
	}
	public Double getImportoRitenute() {
		return importoRitenute;
	}
	public void setImportoRitenute(Double importoRitenute) {
		this.importoRitenute = importoRitenute;
	}
	public Double getImportoMandNetto() {
		return importoMandNetto;
	}
	public void setImportoMandNetto(Double importoMandNetto) {
		this.importoMandNetto = importoMandNetto;
	}
	public Date getDataQuiet() {
		return dataQuiet;
	}
	public void setDataQuiet(Date dataQuiet) {
		this.dataQuiet = dataQuiet;
	}
	public Double getImportoQuiet() {
		return importoQuiet;
	}
	public void setImportoQuiet(Double importoQuiet) {
		this.importoQuiet = importoQuiet;
	}
	public String getCupLiq() {
		return cupLiq;
	}
	public void setCupLiq(String cupLiq) {
		this.cupLiq = cupLiq;
	}
	public String getCigLiq() {
		return cigLiq;
	}
	public void setCigLiq(String cigLiq) {
		this.cigLiq = cigLiq;
	}
	public String getCupMand() {
		return cupMand;
	}
	public void setCupMand(String cupMand) {
		this.cupMand = cupMand;
	}
	public String getCigMand() {
		return cigMand;
	}
	public void setCigMand(String cigMand) {
		this.cigMand = cigMand;
	}
	public String getFlagPign() {
		return flagPign;
	}
	public void setFlagPign(String flagPign) {
		this.flagPign = flagPign;
	}
}
