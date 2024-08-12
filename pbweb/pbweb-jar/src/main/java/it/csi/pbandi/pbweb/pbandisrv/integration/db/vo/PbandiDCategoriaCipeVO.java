
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDCategoriaCipeVO extends GenericVO {

  	
  	private BigDecimal idCategoriaCipe;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idSottoSettoreCipe;
  	
  	private Date dtInizioValidita;
  	
  	private String descCategoriaCipe;
  	
  	private String codCategoriaCipe;
  	
	public PbandiDCategoriaCipeVO() {}
  	
	public PbandiDCategoriaCipeVO (BigDecimal idCategoriaCipe) {
	
		this. idCategoriaCipe =  idCategoriaCipe;
	}
  	
	public PbandiDCategoriaCipeVO (BigDecimal idCategoriaCipe, Date dtFineValidita, BigDecimal idSottoSettoreCipe, Date dtInizioValidita, String descCategoriaCipe, String codCategoriaCipe) {
	
		this. idCategoriaCipe =  idCategoriaCipe;
		this. dtFineValidita =  dtFineValidita;
		this. idSottoSettoreCipe =  idSottoSettoreCipe;
		this. dtInizioValidita =  dtInizioValidita;
		this. descCategoriaCipe =  descCategoriaCipe;
		this. codCategoriaCipe =  codCategoriaCipe;
	}
  	
  	
	public BigDecimal getIdCategoriaCipe() {
		return idCategoriaCipe;
	}
	
	public void setIdCategoriaCipe(BigDecimal idCategoriaCipe) {
		this.idCategoriaCipe = idCategoriaCipe;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdSottoSettoreCipe() {
		return idSottoSettoreCipe;
	}
	
	public void setIdSottoSettoreCipe(BigDecimal idSottoSettoreCipe) {
		this.idSottoSettoreCipe = idSottoSettoreCipe;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescCategoriaCipe() {
		return descCategoriaCipe;
	}
	
	public void setDescCategoriaCipe(String descCategoriaCipe) {
		this.descCategoriaCipe = descCategoriaCipe;
	}
	
	public String getCodCategoriaCipe() {
		return codCategoriaCipe;
	}
	
	public void setCodCategoriaCipe(String codCategoriaCipe) {
		this.codCategoriaCipe = codCategoriaCipe;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idSottoSettoreCipe != null && dtInizioValidita != null && descCategoriaCipe != null && codCategoriaCipe != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idCategoriaCipe != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idCategoriaCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCategoriaCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSottoSettoreCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSottoSettoreCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descCategoriaCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descCategoriaCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codCategoriaCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codCategoriaCipe: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idCategoriaCipe");
		
	    return pk;
	}
	
	
}
