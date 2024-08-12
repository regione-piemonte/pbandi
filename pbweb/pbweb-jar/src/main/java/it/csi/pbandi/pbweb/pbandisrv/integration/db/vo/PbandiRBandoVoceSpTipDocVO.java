
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRBandoVoceSpTipDocVO extends GenericVO {
	
	private BigDecimal idBando;
	private BigDecimal idTipoDocumentoSpesa;
	private BigDecimal idVoceDiSpesa;
	private Date dtInizioAttivita;
	private Date dtFineAttivita;
  	
	public PbandiRBandoVoceSpTipDocVO () {
	}
	
	public PbandiRBandoVoceSpTipDocVO (BigDecimal idBando, BigDecimal idTipoDocumentoSpesa, BigDecimal idVoceDiSpesa) {
		this.idBando = idBando;
		this.idTipoDocumentoSpesa = idTipoDocumentoSpesa;
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
	
	public PbandiRBandoVoceSpTipDocVO (BigDecimal idBando, BigDecimal idTipoDocumentoSpesa, BigDecimal idVoceDiSpesa, Date dtInizioAttivita, Date dtFineAttivita) {
		this.idBando = idBando;
		this.idTipoDocumentoSpesa = idTipoDocumentoSpesa;
		this.idVoceDiSpesa = idVoceDiSpesa;
		this.dtInizioAttivita = dtInizioAttivita;
		this.dtFineAttivita = dtFineAttivita;
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		pk.add("idBando");
		pk.add("idTipoDocumentoSpesa");
		pk.add("idVoceDiSpesa");
	    return pk;
	}
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idBando != null && idTipoDocumentoSpesa != null && idVoceDiSpesa != null) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
  	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBando: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoDocumentoSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoDocumentoSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idVoceDiSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idVoceDiSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioAttivita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioAttivita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineAttivita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineAttivita: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdBando() {
		return idBando;
	}

	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}

	public BigDecimal getIdTipoDocumentoSpesa() {
		return idTipoDocumentoSpesa;
	}

	public void setIdTipoDocumentoSpesa(BigDecimal idTipoDocumentoSpesa) {
		this.idTipoDocumentoSpesa = idTipoDocumentoSpesa;
	}

	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}

	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}

	public Date getDtInizioAttivita() {
		return dtInizioAttivita;
	}

	public void setDtInizioAttivita(Date dtInizioAttivita) {
		this.dtInizioAttivita = dtInizioAttivita;
	}

	public Date getDtFineAttivita() {
		return dtFineAttivita;
	}

	public void setDtFineAttivita(Date dtFineAttivita) {
		this.dtFineAttivita = dtFineAttivita;
	}
}
