/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;

public class PbandiTDettRevocaIrregVO extends GenericVO {
  	
	private BigDecimal idDettRevocaIrreg;  	
	private BigDecimal idRevoca;
	private BigDecimal idIrregolarita;
  	private BigDecimal idClassRevocaIrreg;	
  	private BigDecimal importo;
  	private BigDecimal importoAudit;
  	private String tipologia;
  	
	public PbandiTDettRevocaIrregVO() {}
  	
	public PbandiTDettRevocaIrregVO (BigDecimal idDettRevocaIrreg) {
		this. idDettRevocaIrreg =  idDettRevocaIrreg;
	}
  	
	public PbandiTDettRevocaIrregVO (BigDecimal idDettRevocaIrreg, BigDecimal idRevoca, BigDecimal idIrregolarita, BigDecimal idClassRevocaIrreg, BigDecimal importo, BigDecimal importoAudit, String tipologia) {
		this.idDettRevocaIrreg = idDettRevocaIrreg; 
		this.idRevoca = idRevoca;
		this.idIrregolarita = idIrregolarita; 
		this.idClassRevocaIrreg = idClassRevocaIrreg;
		this.importo = importo;
		this.importoAudit = importoAudit; 
		this.tipologia = tipologia;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idDettRevocaIrreg != null) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();		
			pk.add("idDettRevocaIrreg");
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idDettRevocaIrreg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDettRevocaIrreg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRevoca);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRevoca: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIrregolarita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIrregolarita: " + temp + "\t\n");	    
	    
	    temp = DataFilter.removeNull( idClassRevocaIrreg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idClassRevocaIrreg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoAudit);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoAudit: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tipologia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tipologia: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdDettRevocaIrreg() {
		return idDettRevocaIrreg;
	}

	public void setIdDettRevocaIrreg(BigDecimal idDettRevocaIrreg) {
		this.idDettRevocaIrreg = idDettRevocaIrreg;
	}

	public BigDecimal getIdRevoca() {
		return idRevoca;
	}

	public void setIdRevoca(BigDecimal idRevoca) {
		this.idRevoca = idRevoca;
	}

	public BigDecimal getIdIrregolarita() {
		return idIrregolarita;
	}

	public void setIdIrregolarita(BigDecimal idIrregolarita) {
		this.idIrregolarita = idIrregolarita;
	}

	public BigDecimal getIdClassRevocaIrreg() {
		return idClassRevocaIrreg;
	}

	public void setIdClassRevocaIrreg(BigDecimal idClassRevocaIrreg) {
		this.idClassRevocaIrreg = idClassRevocaIrreg;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public BigDecimal getImportoAudit() {
		return importoAudit;
	}

	public void setImportoAudit(BigDecimal importoAudit) {
		this.importoAudit = importoAudit;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

}
