/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;

public class PbandiDVoceDiEntrataVO extends GenericVO {
  	
  	private BigDecimal idVoceDiEntrata;
  	
  	private String descrizione;
  	
  	private String descrizioneBreve;
  	
  	private String indicazioni;
  	
  	private String flagEdit;
  	
  	private Long flagRisorseProprie;
  	
  	private Date dtInizioValidita;
  	
  	private Date dtFineValidita;
  	
	public PbandiDVoceDiEntrataVO() {}
  	
	public PbandiDVoceDiEntrataVO (BigDecimal idVoceDiEntrata) {
		this. idVoceDiEntrata =  idVoceDiEntrata;
	}
  	
	public PbandiDVoceDiEntrataVO (BigDecimal idVoceDiEntrata,String descrizione,String descrizioneBreve,String indicazioni,String flagEdit,Long flagRisorseProprie,Date dtInizioValidita, Date dtFineValidita) {
		this. idVoceDiEntrata =  idVoceDiEntrata;
		this. descrizione =  descrizione;
		this. descrizioneBreve =  descrizioneBreve;
		this. indicazioni =  indicazioni;
		this. flagEdit =  flagEdit;
		this. flagRisorseProprie =  flagRisorseProprie;
		this. dtInizioValidita =  dtInizioValidita;
		this. dtFineValidita =  dtFineValidita;
	}

	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idVoceDiEntrata != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
			pk.add("idVoceDiEntrata");
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idVoceDiEntrata);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idVoceDiEntrata: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrizione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descrizioneBreve);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrizioneBreve: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( indicazioni);
	    if (!DataFilter.isEmpty(temp)) sb.append(" indicazioni: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagEdit);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagEdit: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagRisorseProprie);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagRisorseProprie: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdVoceDiEntrata() {
		return idVoceDiEntrata;
	}

	public void setIdVoceDiEntrata(BigDecimal idVoceDiEntrata) {
		this.idVoceDiEntrata = idVoceDiEntrata;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizioneBreve() {
		return descrizioneBreve;
	}

	public void setDescrizioneBreve(String descrizioneBreve) {
		this.descrizioneBreve = descrizioneBreve;
	}

	public String getIndicazioni() {
		return indicazioni;
	}

	public void setIndicazioni(String indicazioni) {
		this.indicazioni = indicazioni;
	}

	public String getFlagEdit() {
		return flagEdit;
	}

	public void setFlagEdit(String flagEdit) {
		this.flagEdit = flagEdit;
	}

	public Long getFlagRisorseProprie() {
		return flagRisorseProprie;
	}

	public void setFlagRisorseProprie(Long flagRisorseProprie) {
		this.flagRisorseProprie = flagRisorseProprie;
	}

	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}

	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

}
