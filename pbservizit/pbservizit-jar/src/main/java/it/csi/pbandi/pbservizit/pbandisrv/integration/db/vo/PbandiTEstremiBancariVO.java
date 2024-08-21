/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiTEstremiBancariVO extends GenericVO {

  	
  	private String bic;
  	
  	private String cab;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private String numeroConto;
  	
  	private String iban;
  	
  	private BigDecimal idAgenzia;
  	
  	private BigDecimal idEstremiBancari;
  	
  	private Date dtFineValidita;
  	
  	private String cin;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idBanca;
  	
  	private String abi;
  	
	public PbandiTEstremiBancariVO() {}
  	
	public PbandiTEstremiBancariVO (BigDecimal idEstremiBancari) {
	
		this. idEstremiBancari =  idEstremiBancari;
	}
  	
	public PbandiTEstremiBancariVO (String bic, String cab, Date dtInizioValidita, BigDecimal idUtenteAgg, String numeroConto, String iban, BigDecimal idAgenzia, BigDecimal idEstremiBancari, Date dtFineValidita, String cin, BigDecimal idUtenteIns, BigDecimal idBanca, String abi) {
	
		this. bic =  bic;
		this. cab =  cab;
		this. dtInizioValidita =  dtInizioValidita;
		this. idUtenteAgg =  idUtenteAgg;
		this. numeroConto =  numeroConto;
		this. iban =  iban;
		this. idAgenzia =  idAgenzia;
		this. idEstremiBancari =  idEstremiBancari;
		this. dtFineValidita =  dtFineValidita;
		this. cin =  cin;
		this. idUtenteIns =  idUtenteIns;
		this. idBanca =  idBanca;
		this. abi =  abi;
	}
  	
  	
	public String getBic() {
		return bic;
	}
	
	public void setBic(String bic) {
		this.bic = bic;
	}
	
	public String getCab() {
		return cab;
	}
	
	public void setCab(String cab) {
		this.cab = cab;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public String getNumeroConto() {
		return numeroConto;
	}
	
	public void setNumeroConto(String numeroConto) {
		this.numeroConto = numeroConto;
	}
	
	public String getIban() {
		return iban;
	}
	
	public void setIban(String iban) {
		this.iban = iban;
	}
	
	public BigDecimal getIdAgenzia() {
		return idAgenzia;
	}
	
	public void setIdAgenzia(BigDecimal idAgenzia) {
		this.idAgenzia = idAgenzia;
	}
	
	public BigDecimal getIdEstremiBancari() {
		return idEstremiBancari;
	}
	
	public void setIdEstremiBancari(BigDecimal idEstremiBancari) {
		this.idEstremiBancari = idEstremiBancari;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getCin() {
		return cin;
	}
	
	public void setCin(String cin) {
		this.cin = cin;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdBanca() {
		return idBanca;
	}
	
	public void setIdBanca(BigDecimal idBanca) {
		this.idBanca = idBanca;
	}
	
	public String getAbi() {
		return abi;
	}
	
	public void setAbi(String abi) {
		this.abi = abi;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && iban != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idEstremiBancari != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( bic);
	    if (!DataFilter.isEmpty(temp)) sb.append(" bic: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cab);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cab: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numeroConto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroConto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( iban);
	    if (!DataFilter.isEmpty(temp)) sb.append(" iban: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAgenzia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAgenzia: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idEstremiBancari);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEstremiBancari: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cin);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cin: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idBanca);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBanca: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( abi);
	    if (!DataFilter.isEmpty(temp)) sb.append(" abi: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idEstremiBancari");
		
	    return pk;
	}
	
	
}
