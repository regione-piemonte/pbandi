/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDDeliberaVO extends GenericVO {

  	
  	private BigDecimal numero;
  	
  	private Date dtFineValidita;
  	
  	private String descQuota;
  	
  	private String tipoQuota;
  	
  	private BigDecimal idDelibera;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal anno;
  	
  	private BigDecimal codIgrueT27;
  	
	public PbandiDDeliberaVO() {}
  	
	public PbandiDDeliberaVO (BigDecimal idDelibera) {
	
		this. idDelibera =  idDelibera;
	}
  	
	public PbandiDDeliberaVO (BigDecimal numero, Date dtFineValidita, String descQuota, String tipoQuota, BigDecimal idDelibera, Date dtInizioValidita, BigDecimal anno, BigDecimal codIgrueT27) {
	
		this. numero =  numero;
		this. dtFineValidita =  dtFineValidita;
		this. descQuota =  descQuota;
		this. tipoQuota =  tipoQuota;
		this. idDelibera =  idDelibera;
		this. dtInizioValidita =  dtInizioValidita;
		this. anno =  anno;
		this. codIgrueT27 =  codIgrueT27;
	}
  	
  	
	public BigDecimal getNumero() {
		return numero;
	}
	
	public void setNumero(BigDecimal numero) {
		this.numero = numero;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescQuota() {
		return descQuota;
	}
	
	public void setDescQuota(String descQuota) {
		this.descQuota = descQuota;
	}
	
	public String getTipoQuota() {
		return tipoQuota;
	}
	
	public void setTipoQuota(String tipoQuota) {
		this.tipoQuota = tipoQuota;
	}
	
	public BigDecimal getIdDelibera() {
		return idDelibera;
	}
	
	public void setIdDelibera(BigDecimal idDelibera) {
		this.idDelibera = idDelibera;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getAnno() {
		return anno;
	}
	
	public void setAnno(BigDecimal anno) {
		this.anno = anno;
	}
	
	public BigDecimal getCodIgrueT27() {
		return codIgrueT27;
	}
	
	public void setCodIgrueT27(BigDecimal codIgrueT27) {
		this.codIgrueT27 = codIgrueT27;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && numero != null && dtInizioValidita != null && anno != null && codIgrueT27 != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idDelibera != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( numero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numero: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descQuota);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descQuota: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tipoQuota);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tipoQuota: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDelibera);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDelibera: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( anno);
	    if (!DataFilter.isEmpty(temp)) sb.append(" anno: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT27);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT27: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idDelibera");
		
	    return pk;
	}
	
	
}
