
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDMotivoRevocaVO extends GenericVO {

  	
  	private BigDecimal idMotivoRevoca;
  	
  	private String codIgrueT36;
  	
  	private Date dtFineValidita;
  	
  	private Date dtInizioValidita;
  	
  	private String descMotivoRevoca;
  	
	public PbandiDMotivoRevocaVO() {}
  	
	public PbandiDMotivoRevocaVO (BigDecimal idMotivoRevoca) {
	
		this. idMotivoRevoca =  idMotivoRevoca;
	}
  	
	public PbandiDMotivoRevocaVO (BigDecimal idMotivoRevoca, String codIgrueT36, Date dtFineValidita, Date dtInizioValidita, String descMotivoRevoca) {
	
		this. idMotivoRevoca =  idMotivoRevoca;
		this. codIgrueT36 =  codIgrueT36;
		this. dtFineValidita =  dtFineValidita;
		this. dtInizioValidita =  dtInizioValidita;
		this. descMotivoRevoca =  descMotivoRevoca;
	}
  	
  	
	public BigDecimal getIdMotivoRevoca() {
		return idMotivoRevoca;
	}
	
	public void setIdMotivoRevoca(BigDecimal idMotivoRevoca) {
		this.idMotivoRevoca = idMotivoRevoca;
	}
	
	public String getCodIgrueT36() {
		return codIgrueT36;
	}
	
	public void setCodIgrueT36(String codIgrueT36) {
		this.codIgrueT36 = codIgrueT36;
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
	
	public String getDescMotivoRevoca() {
		return descMotivoRevoca;
	}
	
	public void setDescMotivoRevoca(String descMotivoRevoca) {
		this.descMotivoRevoca = descMotivoRevoca;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codIgrueT36 != null && dtInizioValidita != null && descMotivoRevoca != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idMotivoRevoca != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idMotivoRevoca);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMotivoRevoca: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT36);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT36: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descMotivoRevoca);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descMotivoRevoca: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idMotivoRevoca");
		
	    return pk;
	}
	
	
}
