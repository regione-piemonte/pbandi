/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDFormaGiuridicaVO extends GenericVO {

  	
  	private String divisione;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idFormaGiuridica;
  	
  	private String codFormaGiuridica;
  	
  	private String sezione;
  	
  	private Date dtFineValidita;
  	
  	private String flagPrivato;
  	
  	private String descFormaGiuridica;
  	
  	private String codGefo;
  	
	public PbandiDFormaGiuridicaVO() {}
  	
	public PbandiDFormaGiuridicaVO (BigDecimal idFormaGiuridica) {
	
		this. idFormaGiuridica =  idFormaGiuridica;
	}
  	
	public PbandiDFormaGiuridicaVO (String divisione, Date dtInizioValidita, BigDecimal idFormaGiuridica, String codFormaGiuridica, String sezione, Date dtFineValidita, String flagPrivato, String descFormaGiuridica, String codGefo) {
	
		this. divisione =  divisione;
		this. dtInizioValidita =  dtInizioValidita;
		this. idFormaGiuridica =  idFormaGiuridica;
		this. codFormaGiuridica =  codFormaGiuridica;
		this. sezione =  sezione;
		this. dtFineValidita =  dtFineValidita;
		this. flagPrivato =  flagPrivato;
		this. descFormaGiuridica =  descFormaGiuridica;
		this. codGefo =  codGefo;
	}
  	
  	
	public String getDivisione() {
		return divisione;
	}
	
	public void setDivisione(String divisione) {
		this.divisione = divisione;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdFormaGiuridica() {
		return idFormaGiuridica;
	}
	
	public void setIdFormaGiuridica(BigDecimal idFormaGiuridica) {
		this.idFormaGiuridica = idFormaGiuridica;
	}
	
	public String getCodFormaGiuridica() {
		return codFormaGiuridica;
	}
	
	public void setCodFormaGiuridica(String codFormaGiuridica) {
		this.codFormaGiuridica = codFormaGiuridica;
	}
	
	public String getSezione() {
		return sezione;
	}
	
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getFlagPrivato() {
		return flagPrivato;
	}
	
	public void setFlagPrivato(String flagPrivato) {
		this.flagPrivato = flagPrivato;
	}
	
	public String getDescFormaGiuridica() {
		return descFormaGiuridica;
	}
	
	public void setDescFormaGiuridica(String descFormaGiuridica) {
		this.descFormaGiuridica = descFormaGiuridica;
	}
	
	public String getCodGefo() {
		return codGefo;
	}
	
	public void setCodGefo(String codGefo) {
		this.codGefo = codGefo;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && codFormaGiuridica != null && descFormaGiuridica != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idFormaGiuridica != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( divisione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" divisione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFormaGiuridica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFormaGiuridica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codFormaGiuridica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codFormaGiuridica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( sezione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" sezione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagPrivato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagPrivato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descFormaGiuridica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descFormaGiuridica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codGefo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codGefo: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idFormaGiuridica");
		
	    return pk;
	}
	
	
}
