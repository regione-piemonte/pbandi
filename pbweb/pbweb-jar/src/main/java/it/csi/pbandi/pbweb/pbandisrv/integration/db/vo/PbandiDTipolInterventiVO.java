/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

public class PbandiDTipolInterventiVO extends GenericVO {
	
	private BigDecimal idTipolIntervento;
	private BigDecimal idCampoIntervento;
	private BigDecimal idMacroVoce;
	private String codice;
	private String descrizione;
	
	public PbandiDTipolInterventiVO() {};
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipolIntervento != null) {
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
		pk.add("idTipolIntervento");
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipolIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipolIntervento: " + temp + "\t\n");
	    temp = DataFilter.removeNull( idCampoIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCampoIntervento: " + temp + "\t\n");
	    temp = DataFilter.removeNull( idMacroVoce);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMacroVoce: " + temp + "\t\n");
	    temp = DataFilter.removeNull( codice);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codice: " + temp + "\t\n");
	    temp = DataFilter.removeNull( descrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrizione: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdTipolIntervento() {
		return idTipolIntervento;
	}

	public void setIdTipolIntervento(BigDecimal idTipolIntervento) {
		this.idTipolIntervento = idTipolIntervento;
	}

	public BigDecimal getIdCampoIntervento() {
		return idCampoIntervento;
	}

	public void setIdCampoIntervento(BigDecimal idCampoIntervento) {
		this.idCampoIntervento = idCampoIntervento;
	}

	public BigDecimal getIdMacroVoce() {
		return idMacroVoce;
	}

	public void setIdMacroVoce(BigDecimal idMacroVoce) {
		this.idMacroVoce = idMacroVoce;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
