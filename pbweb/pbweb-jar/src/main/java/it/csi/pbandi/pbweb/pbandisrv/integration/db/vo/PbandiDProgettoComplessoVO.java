
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDProgettoComplessoVO extends GenericVO {

  	
  	private String descProgettoComplesso;
  	
  	private String codTipologia;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idProgettoComplesso;
  	
  	private String codIgrueT9;
  	
  	private Date dtInizioValidita;
  	
  	private String descTipologia;
  	
	public PbandiDProgettoComplessoVO() {}
  	
	public PbandiDProgettoComplessoVO (BigDecimal idProgettoComplesso) {
	
		this. idProgettoComplesso =  idProgettoComplesso;
	}
  	
	public PbandiDProgettoComplessoVO (String descProgettoComplesso, String codTipologia, Date dtFineValidita, BigDecimal idProgettoComplesso, String codIgrueT9, Date dtInizioValidita, String descTipologia) {
	
		this. descProgettoComplesso =  descProgettoComplesso;
		this. codTipologia =  codTipologia;
		this. dtFineValidita =  dtFineValidita;
		this. idProgettoComplesso =  idProgettoComplesso;
		this. codIgrueT9 =  codIgrueT9;
		this. dtInizioValidita =  dtInizioValidita;
		this. descTipologia =  descTipologia;
	}
  	
  	
	public String getDescProgettoComplesso() {
		return descProgettoComplesso;
	}
	
	public void setDescProgettoComplesso(String descProgettoComplesso) {
		this.descProgettoComplesso = descProgettoComplesso;
	}
	
	public String getCodTipologia() {
		return codTipologia;
	}
	
	public void setCodTipologia(String codTipologia) {
		this.codTipologia = codTipologia;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdProgettoComplesso() {
		return idProgettoComplesso;
	}
	
	public void setIdProgettoComplesso(BigDecimal idProgettoComplesso) {
		this.idProgettoComplesso = idProgettoComplesso;
	}
	
	public String getCodIgrueT9() {
		return codIgrueT9;
	}
	
	public void setCodIgrueT9(String codIgrueT9) {
		this.codIgrueT9 = codIgrueT9;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescTipologia() {
		return descTipologia;
	}
	
	public void setDescTipologia(String descTipologia) {
		this.descTipologia = descTipologia;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descProgettoComplesso != null && codTipologia != null && codIgrueT9 != null && dtInizioValidita != null && descTipologia != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idProgettoComplesso != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descProgettoComplesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descProgettoComplesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codTipologia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codTipologia: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgettoComplesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgettoComplesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT9);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT9: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipologia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipologia: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idProgettoComplesso");
		
	    return pk;
	}
	
	
}
