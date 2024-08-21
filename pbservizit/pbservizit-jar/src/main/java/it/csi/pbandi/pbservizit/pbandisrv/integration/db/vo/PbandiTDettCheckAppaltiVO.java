/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiTDettCheckAppaltiVO extends GenericVO {

  	
  	private String codControllo;
  	
  	
  	private String flagEsito;
  	
  	private BigDecimal idAppaltoChecklist;
  	
  	private BigDecimal idDettCheckAppalti;
  	
  	private String note;
  	
	public PbandiTDettCheckAppaltiVO() {}
  	
	public PbandiTDettCheckAppaltiVO (BigDecimal idDettCheckAppalti) {
	
		this. idDettCheckAppalti =  idDettCheckAppalti;
	}
  	
	public PbandiTDettCheckAppaltiVO (String codControllo, String flagEsito, BigDecimal idAppaltoChecklist, BigDecimal idDettCheckAppalti, String note) {
	
		this. codControllo =  codControllo;
		this. flagEsito =  flagEsito;
		this. idAppaltoChecklist =  idAppaltoChecklist;
		this. idDettCheckAppalti =  idDettCheckAppalti;
		this. note =  note;
	}
  	
  	
	public String getCodControllo() {
		return codControllo;
	}
	
	public void setCodControllo(String codControllo) {
		this.codControllo = codControllo;
	}
	
	
	public String getFlagEsito() {
		return flagEsito;
	}
	
	public void setFlagEsito(String flagEsito) {
		this.flagEsito = flagEsito;
	}
	
	public BigDecimal getIdAppaltoChecklist() {
		return idAppaltoChecklist;
	}
	
	public void setIdAppaltoChecklist(BigDecimal idAppaltoChecklist) {
		this.idAppaltoChecklist = idAppaltoChecklist;
	}
	
	public BigDecimal getIdDettCheckAppalti() {
		return idDettCheckAppalti;
	}
	
	public void setIdDettCheckAppalti(BigDecimal idDettCheckAppalti) {
		this.idDettCheckAppalti = idDettCheckAppalti;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codControllo != null && idAppaltoChecklist != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idDettCheckAppalti != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( codControllo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codControllo: " + temp + "\t\n");
	    
	    
	    temp = DataFilter.removeNull( flagEsito);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagEsito: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAppaltoChecklist);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAppaltoChecklist: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDettCheckAppalti);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDettCheckAppalti: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( note);
	    if (!DataFilter.isEmpty(temp)) sb.append(" note: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idDettCheckAppalti");
		
	    return pk;
	}
	
	
}
