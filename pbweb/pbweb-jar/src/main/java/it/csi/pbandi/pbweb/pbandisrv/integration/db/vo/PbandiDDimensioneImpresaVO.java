
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDDimensioneImpresaVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String codGefo;
  	
  	private String descDimensioneImpresa;
  	
  	private String codDimensioneImpresa;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idDimensioneImpresa;
  	
	public PbandiDDimensioneImpresaVO() {}
  	
	public PbandiDDimensioneImpresaVO (BigDecimal idDimensioneImpresa) {
	
		this. idDimensioneImpresa =  idDimensioneImpresa;
	}
  	
	public PbandiDDimensioneImpresaVO (Date dtFineValidita, String codGefo, String descDimensioneImpresa, String codDimensioneImpresa, Date dtInizioValidita, BigDecimal idDimensioneImpresa) {
	
		this. dtFineValidita =  dtFineValidita;
		this. codGefo =  codGefo;
		this. descDimensioneImpresa =  descDimensioneImpresa;
		this. codDimensioneImpresa =  codDimensioneImpresa;
		this. dtInizioValidita =  dtInizioValidita;
		this. idDimensioneImpresa =  idDimensioneImpresa;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getCodGefo() {
		return codGefo;
	}
	
	public void setCodGefo(String codGefo) {
		this.codGefo = codGefo;
	}
	
	public String getDescDimensioneImpresa() {
		return descDimensioneImpresa;
	}
	
	public void setDescDimensioneImpresa(String descDimensioneImpresa) {
		this.descDimensioneImpresa = descDimensioneImpresa;
	}
	
	public String getCodDimensioneImpresa() {
		return codDimensioneImpresa;
	}
	
	public void setCodDimensioneImpresa(String codDimensioneImpresa) {
		this.codDimensioneImpresa = codDimensioneImpresa;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdDimensioneImpresa() {
		return idDimensioneImpresa;
	}
	
	public void setIdDimensioneImpresa(BigDecimal idDimensioneImpresa) {
		this.idDimensioneImpresa = idDimensioneImpresa;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descDimensioneImpresa != null && codDimensioneImpresa != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idDimensioneImpresa != null ) {
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
	    
	    temp = DataFilter.removeNull( codGefo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codGefo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descDimensioneImpresa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descDimensioneImpresa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codDimensioneImpresa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codDimensioneImpresa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDimensioneImpresa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDimensioneImpresa: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idDimensioneImpresa");
		
	    return pk;
	}
	
	
}
