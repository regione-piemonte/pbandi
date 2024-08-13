/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTDomandaVO extends GenericVO {

  	
  	private String titoloProgetto;
  	
  	private String acronimoProgetto;
  	
  	private Date dtInizioProgettoPrevista;
  	
  	private BigDecimal idDomandaPadre;
  	
  	private BigDecimal progrBandoLineaIntervento;
  	
  	private BigDecimal numeroDomandaGefo;
  	
  	private String oraPresentazioneDomanda;
  	
  	private BigDecimal idDomanda;
  	
  	private Date dtInserimento;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idStatoInvioDomanda;
  	
  	private String numeroDomanda;
  	
  	private BigDecimal idStatoDomanda;
  	
  	private Date dtFineProgettoPrevista;
  	
  	private String flagDomandaMaster;
  	
  	private Date dtPresentazioneDomanda;
  	
  	private Date dtOraInvio;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private String numProtocolloDomanda;
  	
  	private Date dtProtocollazioneDomanda;
  	
  	private Date dtAggiornamento;
  	
  	private BigDecimal idTipoAiuto;
  	
	public PbandiTDomandaVO() {}
	
	private BigDecimal idAliquotaRitenuta;
  	
	public PbandiTDomandaVO (BigDecimal idDomanda) {
	
		this. idDomanda =  idDomanda;
	}
  	
	public PbandiTDomandaVO (String titoloProgetto, String acronimoProgetto, Date dtInizioProgettoPrevista, BigDecimal idDomandaPadre, BigDecimal progrBandoLineaIntervento, BigDecimal numeroDomandaGefo, String oraPresentazioneDomanda, BigDecimal idDomanda, Date dtInserimento, BigDecimal idUtenteIns, BigDecimal idStatoInvioDomanda, String numeroDomanda, BigDecimal idStatoDomanda, Date dtFineProgettoPrevista, String flagDomandaMaster, Date dtPresentazioneDomanda, Date dtOraInvio, BigDecimal idUtenteAgg, String numProtocolloDomanda, Date dtProtocollazioneDomanda, Date dtAggiornamento, BigDecimal idTipoAiuto, BigDecimal idAliquotaRitenuta) {
	
		this. titoloProgetto =  titoloProgetto;
		this. acronimoProgetto =  acronimoProgetto;
		this. dtInizioProgettoPrevista =  dtInizioProgettoPrevista;
		this. idDomandaPadre =  idDomandaPadre;
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
		this. numeroDomandaGefo =  numeroDomandaGefo;
		this. oraPresentazioneDomanda =  oraPresentazioneDomanda;
		this. idDomanda =  idDomanda;
		this. dtInserimento =  dtInserimento;
		this. idUtenteIns =  idUtenteIns;
		this. idStatoInvioDomanda =  idStatoInvioDomanda;
		this. numeroDomanda =  numeroDomanda;
		this. idStatoDomanda =  idStatoDomanda;
		this. dtFineProgettoPrevista =  dtFineProgettoPrevista;
		this. flagDomandaMaster =  flagDomandaMaster;
		this. dtPresentazioneDomanda =  dtPresentazioneDomanda;
		this. dtOraInvio =  dtOraInvio;
		this. idUtenteAgg =  idUtenteAgg;
		this. numProtocolloDomanda =  numProtocolloDomanda;
		this. dtProtocollazioneDomanda =  dtProtocollazioneDomanda;
		this. dtAggiornamento =  dtAggiornamento;
		this. idTipoAiuto =  idTipoAiuto;
		this. idAliquotaRitenuta = idAliquotaRitenuta;
	}
  	
  	
	public String getTitoloProgetto() {
		return titoloProgetto;
	}
	
	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}
	
	public String getAcronimoProgetto() {
		return acronimoProgetto;
	}
	
	public void setAcronimoProgetto(String acronimoProgetto) {
		this.acronimoProgetto = acronimoProgetto;
	}
	
	public Date getDtInizioProgettoPrevista() {
		return dtInizioProgettoPrevista;
	}
	
	public void setDtInizioProgettoPrevista(Date dtInizioProgettoPrevista) {
		this.dtInizioProgettoPrevista = dtInizioProgettoPrevista;
	}
	
	public BigDecimal getIdDomandaPadre() {
		return idDomandaPadre;
	}
	
	public void setIdDomandaPadre(BigDecimal idDomandaPadre) {
		this.idDomandaPadre = idDomandaPadre;
	}
	
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	
	public BigDecimal getNumeroDomandaGefo() {
		return numeroDomandaGefo;
	}
	
	public void setNumeroDomandaGefo(BigDecimal numeroDomandaGefo) {
		this.numeroDomandaGefo = numeroDomandaGefo;
	}
	
	public String getOraPresentazioneDomanda() {
		return oraPresentazioneDomanda;
	}
	
	public void setOraPresentazioneDomanda(String oraPresentazioneDomanda) {
		this.oraPresentazioneDomanda = oraPresentazioneDomanda;
	}
	
	public BigDecimal getIdDomanda() {
		return idDomanda;
	}
	
	public void setIdDomanda(BigDecimal idDomanda) {
		this.idDomanda = idDomanda;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdStatoInvioDomanda() {
		return idStatoInvioDomanda;
	}
	
	public void setIdStatoInvioDomanda(BigDecimal idStatoInvioDomanda) {
		this.idStatoInvioDomanda = idStatoInvioDomanda;
	}
	
	public String getNumeroDomanda() {
		return numeroDomanda;
	}
	
	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}
	
	public BigDecimal getIdStatoDomanda() {
		return idStatoDomanda;
	}
	
	public void setIdStatoDomanda(BigDecimal idStatoDomanda) {
		this.idStatoDomanda = idStatoDomanda;
	}
	
	public Date getDtFineProgettoPrevista() {
		return dtFineProgettoPrevista;
	}
	
	public void setDtFineProgettoPrevista(Date dtFineProgettoPrevista) {
		this.dtFineProgettoPrevista = dtFineProgettoPrevista;
	}
	
	public String getFlagDomandaMaster() {
		return flagDomandaMaster;
	}
	
	public void setFlagDomandaMaster(String flagDomandaMaster) {
		this.flagDomandaMaster = flagDomandaMaster;
	}
	
	public Date getDtPresentazioneDomanda() {
		return dtPresentazioneDomanda;
	}
	
	public void setDtPresentazioneDomanda(Date dtPresentazioneDomanda) {
		this.dtPresentazioneDomanda = dtPresentazioneDomanda;
	}
	
	public Date getDtOraInvio() {
		return dtOraInvio;
	}
	
	public void setDtOraInvio(Date dtOraInvio) {
		this.dtOraInvio = dtOraInvio;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public String getNumProtocolloDomanda() {
		return numProtocolloDomanda;
	}
	
	public void setNumProtocolloDomanda(String numProtocolloDomanda) {
		this.numProtocolloDomanda = numProtocolloDomanda;
	}
	
	public Date getDtProtocollazioneDomanda() {
		return dtProtocollazioneDomanda;
	}
	
	public void setDtProtocollazioneDomanda(Date dtProtocollazioneDomanda) {
		this.dtProtocollazioneDomanda = dtProtocollazioneDomanda;
	}
	
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	
	public BigDecimal getIdTipoAiuto() {
		return idTipoAiuto;
	}
	
	public void setIdTipoAiuto(BigDecimal idTipoAiuto) {
		this.idTipoAiuto = idTipoAiuto;
	}
	
	public BigDecimal getIdAliquotaRitenuta() {
		return idAliquotaRitenuta;
	}

	public void setIdAliquotaRitenuta(BigDecimal idAliquotaRitenuta) {
		this.idAliquotaRitenuta = idAliquotaRitenuta;
	}

	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && progrBandoLineaIntervento != null && dtInserimento != null && idUtenteIns != null && idStatoDomanda != null && flagDomandaMaster != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idDomanda != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( titoloProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" titoloProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( acronimoProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" acronimoProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioProgettoPrevista);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioProgettoPrevista: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDomandaPadre);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDomandaPadre: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numeroDomandaGefo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroDomandaGefo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( oraPresentazioneDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" oraPresentazioneDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoInvioDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoInvioDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numeroDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineProgettoPrevista);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineProgettoPrevista: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagDomandaMaster);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagDomandaMaster: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtPresentazioneDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtPresentazioneDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtOraInvio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtOraInvio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numProtocolloDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numProtocolloDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtProtocollazioneDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtProtocollazioneDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoAiuto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoAiuto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAliquotaRitenuta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAliquotaRitenuta: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idDomanda");
		
	    return pk;
	}
	
	
}
