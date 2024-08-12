
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDStatoDomandaVO extends GenericVO {

  	
  	private String descBreveStatoDomanda;
  	
  	private Date dtFineValidita;
  	
  	private String descStatoDomanda;
  	
  	private String codFaseGebaric;
  	
  	private BigDecimal idStatoDomanda;
  	
  	private String codFaseGefo;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDStatoDomandaVO() {}
  	
	public PbandiDStatoDomandaVO (BigDecimal idStatoDomanda) {
	
		this. idStatoDomanda =  idStatoDomanda;
	}
  	
	public PbandiDStatoDomandaVO (String descBreveStatoDomanda, Date dtFineValidita, String descStatoDomanda, String codFaseGebaric, BigDecimal idStatoDomanda, String codFaseGefo, Date dtInizioValidita) {
	
		this. descBreveStatoDomanda =  descBreveStatoDomanda;
		this. dtFineValidita =  dtFineValidita;
		this. descStatoDomanda =  descStatoDomanda;
		this. codFaseGebaric =  codFaseGebaric;
		this. idStatoDomanda =  idStatoDomanda;
		this. codFaseGefo =  codFaseGefo;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public String getDescBreveStatoDomanda() {
		return descBreveStatoDomanda;
	}
	
	public void setDescBreveStatoDomanda(String descBreveStatoDomanda) {
		this.descBreveStatoDomanda = descBreveStatoDomanda;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescStatoDomanda() {
		return descStatoDomanda;
	}
	
	public void setDescStatoDomanda(String descStatoDomanda) {
		this.descStatoDomanda = descStatoDomanda;
	}
	
	public String getCodFaseGebaric() {
		return codFaseGebaric;
	}
	
	public void setCodFaseGebaric(String codFaseGebaric) {
		this.codFaseGebaric = codFaseGebaric;
	}
	
	public BigDecimal getIdStatoDomanda() {
		return idStatoDomanda;
	}
	
	public void setIdStatoDomanda(BigDecimal idStatoDomanda) {
		this.idStatoDomanda = idStatoDomanda;
	}
	
	public String getCodFaseGefo() {
		return codFaseGefo;
	}
	
	public void setCodFaseGefo(String codFaseGefo) {
		this.codFaseGefo = codFaseGefo;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveStatoDomanda != null && descStatoDomanda != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idStatoDomanda != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveStatoDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveStatoDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descStatoDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descStatoDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codFaseGebaric);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codFaseGebaric: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStatoDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codFaseGefo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codFaseGefo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idStatoDomanda");
		
	    return pk;
	}
	
	
}
