
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDDimensioneTerritorVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descDimensioneTerritoriale;
  	
  	private Date dtInizioValidita;
  	
  	private String codIgrueT7;
  	
  	private BigDecimal idDimensioneTerritor;
  	
	public PbandiDDimensioneTerritorVO() {}
  	
	public PbandiDDimensioneTerritorVO (BigDecimal idDimensioneTerritor) {
	
		this. idDimensioneTerritor =  idDimensioneTerritor;
	}
  	
	public PbandiDDimensioneTerritorVO (Date dtFineValidita, String descDimensioneTerritoriale, Date dtInizioValidita, String codIgrueT7, BigDecimal idDimensioneTerritor) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descDimensioneTerritoriale =  descDimensioneTerritoriale;
		this. dtInizioValidita =  dtInizioValidita;
		this. codIgrueT7 =  codIgrueT7;
		this. idDimensioneTerritor =  idDimensioneTerritor;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescDimensioneTerritoriale() {
		return descDimensioneTerritoriale;
	}
	
	public void setDescDimensioneTerritoriale(String descDimensioneTerritoriale) {
		this.descDimensioneTerritoriale = descDimensioneTerritoriale;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getCodIgrueT7() {
		return codIgrueT7;
	}
	
	public void setCodIgrueT7(String codIgrueT7) {
		this.codIgrueT7 = codIgrueT7;
	}
	
	public BigDecimal getIdDimensioneTerritor() {
		return idDimensioneTerritor;
	}
	
	public void setIdDimensioneTerritor(BigDecimal idDimensioneTerritor) {
		this.idDimensioneTerritor = idDimensioneTerritor;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descDimensioneTerritoriale != null && dtInizioValidita != null && codIgrueT7 != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idDimensioneTerritor != null ) {
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
	    
	    temp = DataFilter.removeNull( descDimensioneTerritoriale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descDimensioneTerritoriale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT7);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT7: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDimensioneTerritor);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDimensioneTerritor: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idDimensioneTerritor");
		
	    return pk;
	}
	
	
}
