
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDStrumentoAttuativoVO extends GenericVO {

  	
  	private String responsabile;
  	
  	private BigDecimal idStrumentoAttuativo;
  	
  	private String descStrumentoAttuativo;
  	
  	private String tipologia;
  	
  	private Date dtFineValidita;
  	
  	private String codIgrueT21;
  	
  	private Date dtApprovazione;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDStrumentoAttuativoVO() {}
  	
	public PbandiDStrumentoAttuativoVO (BigDecimal idStrumentoAttuativo) {
	
		this. idStrumentoAttuativo =  idStrumentoAttuativo;
	}
  	
	public PbandiDStrumentoAttuativoVO (String responsabile, BigDecimal idStrumentoAttuativo, String descStrumentoAttuativo, String tipologia, Date dtFineValidita, String codIgrueT21, Date dtApprovazione, Date dtInizioValidita) {
	
		this. responsabile =  responsabile;
		this. idStrumentoAttuativo =  idStrumentoAttuativo;
		this. descStrumentoAttuativo =  descStrumentoAttuativo;
		this. tipologia =  tipologia;
		this. dtFineValidita =  dtFineValidita;
		this. codIgrueT21 =  codIgrueT21;
		this. dtApprovazione =  dtApprovazione;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public String getResponsabile() {
		return responsabile;
	}
	
	public void setResponsabile(String responsabile) {
		this.responsabile = responsabile;
	}
	
	public BigDecimal getIdStrumentoAttuativo() {
		return idStrumentoAttuativo;
	}
	
	public void setIdStrumentoAttuativo(BigDecimal idStrumentoAttuativo) {
		this.idStrumentoAttuativo = idStrumentoAttuativo;
	}
	
	public String getDescStrumentoAttuativo() {
		return descStrumentoAttuativo;
	}
	
	public void setDescStrumentoAttuativo(String descStrumentoAttuativo) {
		this.descStrumentoAttuativo = descStrumentoAttuativo;
	}
	
	public String getTipologia() {
		return tipologia;
	}
	
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getCodIgrueT21() {
		return codIgrueT21;
	}
	
	public void setCodIgrueT21(String codIgrueT21) {
		this.codIgrueT21 = codIgrueT21;
	}
	
	public Date getDtApprovazione() {
		return dtApprovazione;
	}
	
	public void setDtApprovazione(Date dtApprovazione) {
		this.dtApprovazione = dtApprovazione;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && responsabile != null && descStrumentoAttuativo != null && tipologia != null && codIgrueT21 != null && dtApprovazione != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idStrumentoAttuativo != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( responsabile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" responsabile: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStrumentoAttuativo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStrumentoAttuativo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descStrumentoAttuativo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descStrumentoAttuativo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tipologia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tipologia: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT21);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT21: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtApprovazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtApprovazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idStrumentoAttuativo");
		
	    return pk;
	}
	
	
}
