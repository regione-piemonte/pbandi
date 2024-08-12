
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoTrattamentoVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descBreveTipoTrattamento;
  	
  	private BigDecimal idTipoTrattamento;
  	
  	private String descTipoTrattamento;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDTipoTrattamentoVO() {}
  	
	public PbandiDTipoTrattamentoVO (BigDecimal idTipoTrattamento) {
	
		this. idTipoTrattamento =  idTipoTrattamento;
	}
  	
	public PbandiDTipoTrattamentoVO (Date dtFineValidita, String descBreveTipoTrattamento, BigDecimal idTipoTrattamento, String descTipoTrattamento, Date dtInizioValidita) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descBreveTipoTrattamento =  descBreveTipoTrattamento;
		this. idTipoTrattamento =  idTipoTrattamento;
		this. descTipoTrattamento =  descTipoTrattamento;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescBreveTipoTrattamento() {
		return descBreveTipoTrattamento;
	}
	
	public void setDescBreveTipoTrattamento(String descBreveTipoTrattamento) {
		this.descBreveTipoTrattamento = descBreveTipoTrattamento;
	}
	
	public BigDecimal getIdTipoTrattamento() {
		return idTipoTrattamento;
	}
	
	public void setIdTipoTrattamento(BigDecimal idTipoTrattamento) {
		this.idTipoTrattamento = idTipoTrattamento;
	}
	
	public String getDescTipoTrattamento() {
		return descTipoTrattamento;
	}
	
	public void setDescTipoTrattamento(String descTipoTrattamento) {
		this.descTipoTrattamento = descTipoTrattamento;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveTipoTrattamento != null && descTipoTrattamento != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoTrattamento != null ) {
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
	    
	    temp = DataFilter.removeNull( descBreveTipoTrattamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoTrattamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoTrattamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoTrattamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoTrattamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoTrattamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoTrattamento");
		
	    return pk;
	}
	
	
}
