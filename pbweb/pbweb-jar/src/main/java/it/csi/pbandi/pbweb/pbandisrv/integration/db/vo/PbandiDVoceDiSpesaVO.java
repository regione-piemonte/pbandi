/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

// Cultura: aggiunti idTipologiaVoceDiSpesa e flagEdit.

public class PbandiDVoceDiSpesaVO extends GenericVO {

  	
  	private BigDecimal idVoceDiSpesa;
  	
  	private Date dtFineValidita;
  	
  	private String codTipoVoceDiSpesa;
  	
  	private BigDecimal idVoceDiSpesaMonit;
  	
  	private Date dtInizioValidita;
  	
  	private String descVoceDiSpesa;
  	
  	private BigDecimal idVoceDiSpesaPadre;
  	
  	private Long idTipologiaVoceDiSpesa;
  	
  	private String flagEdit;
  	
	public PbandiDVoceDiSpesaVO() {}
  	
	public PbandiDVoceDiSpesaVO (BigDecimal idVoceDiSpesa) {
	
		this. idVoceDiSpesa =  idVoceDiSpesa;
	}
  	
	public PbandiDVoceDiSpesaVO (BigDecimal idVoceDiSpesa, Date dtFineValidita, String codTipoVoceDiSpesa, BigDecimal idVoceDiSpesaMonit, Date dtInizioValidita, String descVoceDiSpesa, BigDecimal idVoceDiSpesaPadre, Long idTipologiaVoceDiSpesa, String flagEdit) {
	
		this. idVoceDiSpesa =  idVoceDiSpesa;
		this. dtFineValidita =  dtFineValidita;
		this. codTipoVoceDiSpesa =  codTipoVoceDiSpesa;
		this. idVoceDiSpesaMonit =  idVoceDiSpesaMonit;
		this. dtInizioValidita =  dtInizioValidita;
		this. descVoceDiSpesa =  descVoceDiSpesa;
		this. idVoceDiSpesaPadre =  idVoceDiSpesaPadre;
		this. idTipologiaVoceDiSpesa =  idTipologiaVoceDiSpesa;
		this. flagEdit =  flagEdit;
	}
  	
  	
	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}
	
	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getCodTipoVoceDiSpesa() {
		return codTipoVoceDiSpesa;
	}
	
	public void setCodTipoVoceDiSpesa(String codTipoVoceDiSpesa) {
		this.codTipoVoceDiSpesa = codTipoVoceDiSpesa;
	}
	
	public BigDecimal getIdVoceDiSpesaMonit() {
		return idVoceDiSpesaMonit;
	}
	
	public void setIdVoceDiSpesaMonit(BigDecimal idVoceDiSpesaMonit) {
		this.idVoceDiSpesaMonit = idVoceDiSpesaMonit;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}
	
	public void setDescVoceDiSpesa(String descVoceDiSpesa) {
		this.descVoceDiSpesa = descVoceDiSpesa;
	}
	
	public BigDecimal getIdVoceDiSpesaPadre() {
		return idVoceDiSpesaPadre;
	}
	
	public void setIdVoceDiSpesaPadre(BigDecimal idVoceDiSpesaPadre) {
		this.idVoceDiSpesaPadre = idVoceDiSpesaPadre;
	}
	
	public Long getIdTipologiaVoceDiSpesa() {
		return idTipologiaVoceDiSpesa;
	}

	public void setIdTipologiaVoceDiSpesa(Long idTipologiaVoceDiSpesa) {
		this.idTipologiaVoceDiSpesa = idTipologiaVoceDiSpesa;
	}

	public String getFlagEdit() {
		return flagEdit;
	}

	public void setFlagEdit(String flagEdit) {
		this.flagEdit = flagEdit;
	}

	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codTipoVoceDiSpesa != null && dtInizioValidita != null && descVoceDiSpesa != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idVoceDiSpesa != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idVoceDiSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idVoceDiSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codTipoVoceDiSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codTipoVoceDiSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idVoceDiSpesaMonit);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idVoceDiSpesaMonit: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descVoceDiSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descVoceDiSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idVoceDiSpesaPadre);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idVoceDiSpesaPadre: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipologiaVoceDiSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipologiaVoceDiSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagEdit);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagEdit: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idVoceDiSpesa");
		
	    return pk;
	}
	
	
}
