
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDQualificazioneIrregVO extends GenericVO {

  	
  	private String descBreveQualificIrreg;
  	
  	private Date dtFineValidita;
  	
  	private String descQualificazioneIrreg;
  	
  	private BigDecimal idQualificazioneIrreg;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDQualificazioneIrregVO() {}
  	
	public PbandiDQualificazioneIrregVO (BigDecimal idQualificazioneIrreg) {
	
		this. idQualificazioneIrreg =  idQualificazioneIrreg;
	}
  	
	public PbandiDQualificazioneIrregVO (String descBreveQualificIrreg, Date dtFineValidita, String descQualificazioneIrreg, BigDecimal idQualificazioneIrreg, Date dtInizioValidita) {
	
		this. descBreveQualificIrreg =  descBreveQualificIrreg;
		this. dtFineValidita =  dtFineValidita;
		this. descQualificazioneIrreg =  descQualificazioneIrreg;
		this. idQualificazioneIrreg =  idQualificazioneIrreg;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public String getDescBreveQualificIrreg() {
		return descBreveQualificIrreg;
	}
	
	public void setDescBreveQualificIrreg(String descBreveQualificIrreg) {
		this.descBreveQualificIrreg = descBreveQualificIrreg;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescQualificazioneIrreg() {
		return descQualificazioneIrreg;
	}
	
	public void setDescQualificazioneIrreg(String descQualificazioneIrreg) {
		this.descQualificazioneIrreg = descQualificazioneIrreg;
	}
	
	public BigDecimal getIdQualificazioneIrreg() {
		return idQualificazioneIrreg;
	}
	
	public void setIdQualificazioneIrreg(BigDecimal idQualificazioneIrreg) {
		this.idQualificazioneIrreg = idQualificazioneIrreg;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveQualificIrreg != null && descQualificazioneIrreg != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idQualificazioneIrreg != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveQualificIrreg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveQualificIrreg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descQualificazioneIrreg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descQualificazioneIrreg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idQualificazioneIrreg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idQualificazioneIrreg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idQualificazioneIrreg");
		
	    return pk;
	}
	
	
}
