/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoOperazioneVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idTipoOperazione;
  	
  	private String codIgrueT0;
  	
  	private String descBreveTipoOperazione;
  	
  	private Date dtInizioValidita;
  	
  	private String descTipoOperazione;
  	
	public PbandiDTipoOperazioneVO() {}
  	
	public PbandiDTipoOperazioneVO (BigDecimal idTipoOperazione) {
	
		this. idTipoOperazione =  idTipoOperazione;
	}
  	
	public PbandiDTipoOperazioneVO (Date dtFineValidita, BigDecimal idTipoOperazione, String codIgrueT0, String descBreveTipoOperazione, Date dtInizioValidita, String descTipoOperazione) {
	
		this. dtFineValidita =  dtFineValidita;
		this. idTipoOperazione =  idTipoOperazione;
		this. codIgrueT0 =  codIgrueT0;
		this. descBreveTipoOperazione =  descBreveTipoOperazione;
		this. dtInizioValidita =  dtInizioValidita;
		this. descTipoOperazione =  descTipoOperazione;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdTipoOperazione() {
		return idTipoOperazione;
	}
	
	public void setIdTipoOperazione(BigDecimal idTipoOperazione) {
		this.idTipoOperazione = idTipoOperazione;
	}
	
	public String getCodIgrueT0() {
		return codIgrueT0;
	}
	
	public void setCodIgrueT0(String codIgrueT0) {
		this.codIgrueT0 = codIgrueT0;
	}
	
	public String getDescBreveTipoOperazione() {
		return descBreveTipoOperazione;
	}
	
	public void setDescBreveTipoOperazione(String descBreveTipoOperazione) {
		this.descBreveTipoOperazione = descBreveTipoOperazione;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescTipoOperazione() {
		return descTipoOperazione;
	}
	
	public void setDescTipoOperazione(String descTipoOperazione) {
		this.descTipoOperazione = descTipoOperazione;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveTipoOperazione != null && dtInizioValidita != null && descTipoOperazione != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoOperazione != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoOperazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoOperazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT0);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT0: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoOperazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoOperazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoOperazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoOperazione: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoOperazione");
		
	    return pk;
	}
	
	
}
