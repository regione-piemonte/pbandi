
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import java.util.List;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDMacroSezioneModuloVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descBreveMacroSezione;
  	
  	private String descMacroSezione;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idMacroSezioneModulo;
  	
	public PbandiDMacroSezioneModuloVO() {}
  	
	public PbandiDMacroSezioneModuloVO (BigDecimal idMacroSezioneModulo) {
	
		this. idMacroSezioneModulo =  idMacroSezioneModulo;
	}
  	
	public PbandiDMacroSezioneModuloVO (Date dtFineValidita, String descBreveMacroSezione, String descMacroSezione, Date dtInizioValidita, BigDecimal idMacroSezioneModulo) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descBreveMacroSezione =  descBreveMacroSezione;
		this. descMacroSezione =  descMacroSezione;
		this. dtInizioValidita =  dtInizioValidita;
		this. idMacroSezioneModulo =  idMacroSezioneModulo;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescBreveMacroSezione() {
		return descBreveMacroSezione;
	}
	
	public void setDescBreveMacroSezione(String descBreveMacroSezione) {
		this.descBreveMacroSezione = descBreveMacroSezione;
	}
	
	public String getDescMacroSezione() {
		return descMacroSezione;
	}
	
	public void setDescMacroSezione(String descMacroSezione) {
		this.descMacroSezione = descMacroSezione;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdMacroSezioneModulo() {
		return idMacroSezioneModulo;
	}
	
	public void setIdMacroSezioneModulo(BigDecimal idMacroSezioneModulo) {
		this.idMacroSezioneModulo = idMacroSezioneModulo;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveMacroSezione != null && descMacroSezione != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idMacroSezioneModulo != null ) {
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
	    
	    temp = DataFilter.removeNull( descBreveMacroSezione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveMacroSezione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descMacroSezione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descMacroSezione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idMacroSezioneModulo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMacroSezioneModulo: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	@Override
	public List getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
		pk.add("idMacroSezioneModulo");
	
    return pk;
	}
	
	
	
}
