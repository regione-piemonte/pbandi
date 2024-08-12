
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTDocumentoIndexLockVO extends GenericVO {

  	
  	private BigDecimal idTipoDocumentoIndex;
  	
  	private BigDecimal idTarget;
  	
  	private Date dtLockDocumento;
  	
  	private BigDecimal idUtente;
  	
  	private BigDecimal idEntita;
  	
  	private BigDecimal idProgetto;
  	
	public PbandiTDocumentoIndexLockVO() {}
  	
	public PbandiTDocumentoIndexLockVO (BigDecimal idTipoDocumentoIndex, BigDecimal idTarget, BigDecimal idEntita, BigDecimal idProgetto) {
	
		this. idTipoDocumentoIndex =  idTipoDocumentoIndex;
		this. idTarget =  idTarget;
		this. idEntita =  idEntita;
		this. idProgetto =  idProgetto;
	}
  	
	public PbandiTDocumentoIndexLockVO (BigDecimal idTipoDocumentoIndex, BigDecimal idTarget, Date dtLockDocumento, BigDecimal idUtente, BigDecimal idEntita, BigDecimal idProgetto) {
	
		this. idTipoDocumentoIndex =  idTipoDocumentoIndex;
		this. idTarget =  idTarget;
		this. dtLockDocumento =  dtLockDocumento;
		this. idUtente =  idUtente;
		this. idEntita =  idEntita;
		this. idProgetto =  idProgetto;
	}
  	
  	
	public BigDecimal getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}
	
	public void setIdTipoDocumentoIndex(BigDecimal idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}
	
	public BigDecimal getIdTarget() {
		return idTarget;
	}
	
	public void setIdTarget(BigDecimal idTarget) {
		this.idTarget = idTarget;
	}
	
	public Date getDtLockDocumento() {
		return dtLockDocumento;
	}
	
	public void setDtLockDocumento(Date dtLockDocumento) {
		this.dtLockDocumento = dtLockDocumento;
	}
	
	public BigDecimal getIdUtente() {
		return idUtente;
	}
	
	public void setIdUtente(BigDecimal idUtente) {
		this.idUtente = idUtente;
	}
	
	public BigDecimal getIdEntita() {
		return idEntita;
	}
	
	public void setIdEntita(BigDecimal idEntita) {
		this.idEntita = idEntita;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtLockDocumento != null && idUtente != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoDocumentoIndex != null && idTarget != null && idEntita != null && idProgetto != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoDocumentoIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoDocumentoIndex: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTarget);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTarget: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtLockDocumento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtLockDocumento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtente);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtente: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idEntita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEntita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoDocumentoIndex");
		
			pk.add("idTarget");
		
			pk.add("idEntita");
		
			pk.add("idProgetto");
		
	    return pk;
	}
	
	
}
