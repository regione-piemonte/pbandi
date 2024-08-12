package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PbandiTEsitiNoteAffidamentVO extends GenericVO {
	
	private BigDecimal idEsito;
	private BigDecimal fase;
	private String esito;
	private BigDecimal idChecklist;
	private Date dataIns;
	private String note;
	private BigDecimal idUtenteIns;
	private String flagRettifica;		// Jira PBANDI-2829.
	
	public PbandiTEsitiNoteAffidamentVO() {}
	
	public PbandiTEsitiNoteAffidamentVO (BigDecimal idEsito) {
		this.idEsito =  idEsito;
	}
	
	public PbandiTEsitiNoteAffidamentVO (BigDecimal idEsito, BigDecimal fase, String esito, BigDecimal idChecklist, Date dataIns, String note, BigDecimal idUtenteIns, String flagRettifica) {
		this.idEsito = idEsito;
		this.fase = fase;
		this.esito = esito;
		this.idChecklist = idChecklist;
		this.dataIns = dataIns;
		this.note = note;
		this.idUtenteIns = idUtenteIns;	
		this.flagRettifica = flagRettifica;
	}
	

	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
			pk.add("idEsito");
	    return pk;
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
		if (idEsito != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}

	public BigDecimal getIdEsito() {
		return idEsito;
	}

	public void setIdEsito(BigDecimal idEsito) {
		this.idEsito = idEsito;
	}

	public BigDecimal getFase() {
		return fase;
	}

	public void setFase(BigDecimal fase) {
		this.fase = fase;
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public BigDecimal getIdChecklist() {
		return idChecklist;
	}

	public void setIdChecklist(BigDecimal idChecklist) {
		this.idChecklist = idChecklist;
	}

	public Date getDataIns() {
		return dataIns;
	}

	public void setDataIns(Date dataIns) {
		this.dataIns = dataIns;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public String getFlagRettifica() {
		return flagRettifica;
	}

	public void setFlagRettifica(String flagRettifica) {
		this.flagRettifica = flagRettifica;
	}

	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idEsito);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEsito: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( fase);
	    if (!DataFilter.isEmpty(temp)) sb.append(" fase: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( esito);
	    if (!DataFilter.isEmpty(temp)) sb.append(" esito: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagRettifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagRettifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( note);
	    if (!DataFilter.isEmpty(temp)) sb.append(" note: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idChecklist);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idChecklist: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    return sb.toString();
	}

}
