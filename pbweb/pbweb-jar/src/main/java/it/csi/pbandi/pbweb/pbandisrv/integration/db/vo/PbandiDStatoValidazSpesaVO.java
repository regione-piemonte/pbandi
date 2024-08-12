
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDStatoValidazSpesaVO extends GenericVO {

  	
  	private String descBreveStatoValidazSpesa;
  	
  	private String descStatoValidazioneSpesa;
  	
  	private BigDecimal idStatoValidazioneSpesa;
  	
  	private Date dtFineValidita;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDStatoValidazSpesaVO() {}
  	
	public PbandiDStatoValidazSpesaVO (BigDecimal idStatoValidazioneSpesa) {
	
		this. idStatoValidazioneSpesa =  idStatoValidazioneSpesa;
	}
  	
	public PbandiDStatoValidazSpesaVO (String descBreveStatoValidazSpesa, String descStatoValidazioneSpesa, BigDecimal idStatoValidazioneSpesa, Date dtFineValidita, Date dtInizioValidita) {
	
		this. descBreveStatoValidazSpesa =  descBreveStatoValidazSpesa;
		this. descStatoValidazioneSpesa =  descStatoValidazioneSpesa;
		this. idStatoValidazioneSpesa =  idStatoValidazioneSpesa;
		this. dtFineValidita =  dtFineValidita;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public String getDescBreveStatoValidazSpesa() {
		return descBreveStatoValidazSpesa;
	}
	
	public void setDescBreveStatoValidazSpesa(String descBreveStatoValidazSpesa) {
		this.descBreveStatoValidazSpesa = descBreveStatoValidazSpesa;
	}
	
	public String getDescStatoValidazioneSpesa() {
		return descStatoValidazioneSpesa;
	}
	
	public void setDescStatoValidazioneSpesa(String descStatoValidazioneSpesa) {
		this.descStatoValidazioneSpesa = descStatoValidazioneSpesa;
	}
	
	public BigDecimal getIdStatoValidazioneSpesa() {
		return idStatoValidazioneSpesa;
	}
	
	public void setIdStatoValidazioneSpesa(BigDecimal idStatoValidazioneSpesa) {
		this.idStatoValidazioneSpesa = idStatoValidazioneSpesa;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveStatoValidazSpesa != null && descStatoValidazioneSpesa != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idStatoValidazioneSpesa != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveStatoValidazSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveStatoValidazSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descStatoValidazioneSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descStatoValidazioneSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoValidazioneSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoValidazioneSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idStatoValidazioneSpesa");
		
	    return pk;
	}
	
	
}
