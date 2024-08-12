
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDStatoAmministrativoVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descBreveStatoAmministrativ;
  	
  	private String descStatoAmministrativo;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idStatoAmministrativo;
  	
	public PbandiDStatoAmministrativoVO() {}
  	
	public PbandiDStatoAmministrativoVO (BigDecimal idStatoAmministrativo) {
	
		this. idStatoAmministrativo =  idStatoAmministrativo;
	}
  	
	public PbandiDStatoAmministrativoVO (Date dtFineValidita, String descBreveStatoAmministrativ, String descStatoAmministrativo, Date dtInizioValidita, BigDecimal idStatoAmministrativo) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descBreveStatoAmministrativ =  descBreveStatoAmministrativ;
		this. descStatoAmministrativo =  descStatoAmministrativo;
		this. dtInizioValidita =  dtInizioValidita;
		this. idStatoAmministrativo =  idStatoAmministrativo;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescBreveStatoAmministrativ() {
		return descBreveStatoAmministrativ;
	}
	
	public void setDescBreveStatoAmministrativ(String descBreveStatoAmministrativ) {
		this.descBreveStatoAmministrativ = descBreveStatoAmministrativ;
	}
	
	public String getDescStatoAmministrativo() {
		return descStatoAmministrativo;
	}
	
	public void setDescStatoAmministrativo(String descStatoAmministrativo) {
		this.descStatoAmministrativo = descStatoAmministrativo;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdStatoAmministrativo() {
		return idStatoAmministrativo;
	}
	
	public void setIdStatoAmministrativo(BigDecimal idStatoAmministrativo) {
		this.idStatoAmministrativo = idStatoAmministrativo;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveStatoAmministrativ != null && descStatoAmministrativo != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idStatoAmministrativo != null ) {
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
	    
	    temp = DataFilter.removeNull( descBreveStatoAmministrativ);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveStatoAmministrativ: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descStatoAmministrativo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descStatoAmministrativo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoAmministrativo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoAmministrativo: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idStatoAmministrativo");
		
	    return pk;
	}
	
	
}
