/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;

public class PbandiDTipolVoceDiSpesaVO extends GenericVO {
	
	private BigDecimal idTipologiaVoceDiSpesa;
	private String descrizione;
	
	public PbandiDTipolVoceDiSpesaVO() {};
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipologiaVoceDiSpesa != null) {
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
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk = new java.util.ArrayList<String>();
		pk.add("idTipologiaVoceDiSpesa");
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipologiaVoceDiSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipologiaVoceDiSpesa: " + temp + "\t\n");
	    temp = DataFilter.removeNull( descrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrizione: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdTipologiaVoceDiSpesa() {
		return idTipologiaVoceDiSpesa;
	}

	public void setIdTipologiaVoceDiSpesa(BigDecimal idTipologiaVoceDiSpesa) {
		this.idTipologiaVoceDiSpesa = idTipologiaVoceDiSpesa;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
