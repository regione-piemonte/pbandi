
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTScartiCaricaMassDsVO extends GenericVO {

  	
  	private BigDecimal idCaricaMassDocSpesa;
  	
  	private String descTipoDocSpesa;
  	
  	private String codiceFiscaleFornitore;
  	
  	private String codiceErrore;
  	
  	private Date dtDocumento;
  	
  	private BigDecimal idScartiCaricaMassDs;
  	
  	private String numeroDocumento;
  	
  	private String rigaErrore;
  	
	public PbandiTScartiCaricaMassDsVO() {}
  	
	public PbandiTScartiCaricaMassDsVO (BigDecimal idScartiCaricaMassDs) {
	
		this. idScartiCaricaMassDs =  idScartiCaricaMassDs;
	}
  	
	public PbandiTScartiCaricaMassDsVO (BigDecimal idCaricaMassDocSpesa, String descTipoDocSpesa, String codiceFiscaleFornitore, String codiceErrore, Date dtDocumento, BigDecimal idScartiCaricaMassDs, String numeroDocumento, String rigaErrore) {
	
		this. idCaricaMassDocSpesa =  idCaricaMassDocSpesa;
		this. descTipoDocSpesa =  descTipoDocSpesa;
		this. codiceFiscaleFornitore =  codiceFiscaleFornitore;
		this. codiceErrore =  codiceErrore;
		this. dtDocumento =  dtDocumento;
		this. idScartiCaricaMassDs =  idScartiCaricaMassDs;
		this. numeroDocumento =  numeroDocumento;
		this. rigaErrore =  rigaErrore;
	}
  	
  	
	public BigDecimal getIdCaricaMassDocSpesa() {
		return idCaricaMassDocSpesa;
	}
	
	public void setIdCaricaMassDocSpesa(BigDecimal idCaricaMassDocSpesa) {
		this.idCaricaMassDocSpesa = idCaricaMassDocSpesa;
	}
	
	public String getDescTipoDocSpesa() {
		return descTipoDocSpesa;
	}
	
	public void setDescTipoDocSpesa(String descTipoDocSpesa) {
		this.descTipoDocSpesa = descTipoDocSpesa;
	}
	
	public String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}
	
	public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}
	
	public String getCodiceErrore() {
		return codiceErrore;
	}
	
	public void setCodiceErrore(String codiceErrore) {
		this.codiceErrore = codiceErrore;
	}
	
	public Date getDtDocumento() {
		return dtDocumento;
	}
	
	public void setDtDocumento(Date dtDocumento) {
		this.dtDocumento = dtDocumento;
	}
	
	public BigDecimal getIdScartiCaricaMassDs() {
		return idScartiCaricaMassDs;
	}
	
	public void setIdScartiCaricaMassDs(BigDecimal idScartiCaricaMassDs) {
		this.idScartiCaricaMassDs = idScartiCaricaMassDs;
	}
	
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	
	public String getRigaErrore() {
		return rigaErrore;
	}
	
	public void setRigaErrore(String rigaErrore) {
		this.rigaErrore = rigaErrore;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idCaricaMassDocSpesa != null && codiceErrore != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idScartiCaricaMassDs != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idCaricaMassDocSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCaricaMassDocSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoDocSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoDocSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codiceFiscaleFornitore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codiceFiscaleFornitore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codiceErrore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codiceErrore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtDocumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idScartiCaricaMassDs);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idScartiCaricaMassDs: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numeroDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroDocumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( rigaErrore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" rigaErrore: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idScartiCaricaMassDs");
		
	    return pk;
	}
	
	
}
