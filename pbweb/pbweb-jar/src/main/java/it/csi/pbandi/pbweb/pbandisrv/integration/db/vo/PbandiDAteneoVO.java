
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDAteneoVO extends GenericVO {

  	
  	private String codiceFiscaleAteneo;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idFormaGiuridica;
  	
  	private String descBreveAteneo;
  	
  	private BigDecimal idAteneo;
  	
  	private Date dtInizioValidita;
  	
  	private String descAteneo;
  	
  	private BigDecimal idAttivitaAteco;
  	
	public PbandiDAteneoVO() {}
  	
	public PbandiDAteneoVO (BigDecimal idAteneo) {
	
		this. idAteneo =  idAteneo;
	}
  	
	public PbandiDAteneoVO (String codiceFiscaleAteneo, Date dtFineValidita, BigDecimal idFormaGiuridica, String descBreveAteneo, BigDecimal idAteneo, Date dtInizioValidita, String descAteneo, BigDecimal idAttivitaAteco) {
	
		this. codiceFiscaleAteneo =  codiceFiscaleAteneo;
		this. dtFineValidita =  dtFineValidita;
		this. idFormaGiuridica =  idFormaGiuridica;
		this. descBreveAteneo =  descBreveAteneo;
		this. idAteneo =  idAteneo;
		this. dtInizioValidita =  dtInizioValidita;
		this. descAteneo =  descAteneo;
		this. idAttivitaAteco =  idAttivitaAteco;
	}
  	
  	
	public String getCodiceFiscaleAteneo() {
		return codiceFiscaleAteneo;
	}
	
	public void setCodiceFiscaleAteneo(String codiceFiscaleAteneo) {
		this.codiceFiscaleAteneo = codiceFiscaleAteneo;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdFormaGiuridica() {
		return idFormaGiuridica;
	}
	
	public void setIdFormaGiuridica(BigDecimal idFormaGiuridica) {
		this.idFormaGiuridica = idFormaGiuridica;
	}
	
	public String getDescBreveAteneo() {
		return descBreveAteneo;
	}
	
	public void setDescBreveAteneo(String descBreveAteneo) {
		this.descBreveAteneo = descBreveAteneo;
	}
	
	public BigDecimal getIdAteneo() {
		return idAteneo;
	}
	
	public void setIdAteneo(BigDecimal idAteneo) {
		this.idAteneo = idAteneo;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescAteneo() {
		return descAteneo;
	}
	
	public void setDescAteneo(String descAteneo) {
		this.descAteneo = descAteneo;
	}
	
	public BigDecimal getIdAttivitaAteco() {
		return idAttivitaAteco;
	}
	
	public void setIdAttivitaAteco(BigDecimal idAttivitaAteco) {
		this.idAttivitaAteco = idAttivitaAteco;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveAteneo != null && dtInizioValidita != null && descAteneo != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAteneo != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( codiceFiscaleAteneo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codiceFiscaleAteneo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFormaGiuridica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFormaGiuridica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveAteneo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveAteneo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAteneo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAteneo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descAteneo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descAteneo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttivitaAteco);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttivitaAteco: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idAteneo");
		
	    return pk;
	}
	
	
}
