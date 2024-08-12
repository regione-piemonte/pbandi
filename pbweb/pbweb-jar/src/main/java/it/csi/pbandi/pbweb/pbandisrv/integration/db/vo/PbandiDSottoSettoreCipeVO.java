
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDSottoSettoreCipeVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descSottoSettoreCipe;
  	
  	private String codSottoSettoreCipe;
  	
  	private BigDecimal idSottoSettoreCipe;
  	
  	private BigDecimal idSettoreCipe;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDSottoSettoreCipeVO() {}
  	
	public PbandiDSottoSettoreCipeVO (BigDecimal idSottoSettoreCipe) {
	
		this. idSottoSettoreCipe =  idSottoSettoreCipe;
	}
  	
	public PbandiDSottoSettoreCipeVO (Date dtFineValidita, String descSottoSettoreCipe, String codSottoSettoreCipe, BigDecimal idSottoSettoreCipe, BigDecimal idSettoreCipe, Date dtInizioValidita) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descSottoSettoreCipe =  descSottoSettoreCipe;
		this. codSottoSettoreCipe =  codSottoSettoreCipe;
		this. idSottoSettoreCipe =  idSottoSettoreCipe;
		this. idSettoreCipe =  idSettoreCipe;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescSottoSettoreCipe() {
		return descSottoSettoreCipe;
	}
	
	public void setDescSottoSettoreCipe(String descSottoSettoreCipe) {
		this.descSottoSettoreCipe = descSottoSettoreCipe;
	}
	
	public String getCodSottoSettoreCipe() {
		return codSottoSettoreCipe;
	}
	
	public void setCodSottoSettoreCipe(String codSottoSettoreCipe) {
		this.codSottoSettoreCipe = codSottoSettoreCipe;
	}
	
	public BigDecimal getIdSottoSettoreCipe() {
		return idSottoSettoreCipe;
	}
	
	public void setIdSottoSettoreCipe(BigDecimal idSottoSettoreCipe) {
		this.idSottoSettoreCipe = idSottoSettoreCipe;
	}
	
	public BigDecimal getIdSettoreCipe() {
		return idSettoreCipe;
	}
	
	public void setIdSettoreCipe(BigDecimal idSettoreCipe) {
		this.idSettoreCipe = idSettoreCipe;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descSottoSettoreCipe != null && codSottoSettoreCipe != null && idSettoreCipe != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idSottoSettoreCipe != null ) {
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
	    
	    temp = DataFilter.removeNull( descSottoSettoreCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descSottoSettoreCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codSottoSettoreCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codSottoSettoreCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSottoSettoreCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSottoSettoreCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSettoreCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSettoreCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idSottoSettoreCipe");
		
	    return pk;
	}
	
	
}
