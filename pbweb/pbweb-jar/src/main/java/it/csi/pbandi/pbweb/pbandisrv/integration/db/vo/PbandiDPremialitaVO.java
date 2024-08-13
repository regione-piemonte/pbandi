/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;
import java.math.BigDecimal;

public class PbandiDPremialitaVO extends GenericVO {
	
	private BigDecimal idPremialita;
	private String descrizione;
	private String flagTipoDatoRichiesto;
	private String datoRichiesto;
	private String link;
	
	public PbandiDPremialitaVO() {};
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idPremialita != null) {
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
		pk.add("idPremialita");
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idPremialita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPremialita: " + temp + "\t\n");
	    temp = DataFilter.removeNull( descrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrizione: " + temp + "\t\n");
	    temp = DataFilter.removeNull( flagTipoDatoRichiesto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagTipoDatoRichiesto: " + temp + "\t\n");
	    temp = DataFilter.removeNull( datoRichiesto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" datoRichiesto: " + temp + "\t\n");
	    temp = DataFilter.removeNull( link);
	    if (!DataFilter.isEmpty(temp)) sb.append(" link: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdPremialita() {
		return idPremialita;
	}

	public void setIdPremialita(BigDecimal idPremialita) {
		this.idPremialita = idPremialita;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getFlagTipoDatoRichiesto() {
		return flagTipoDatoRichiesto;
	}

	public void setFlagTipoDatoRichiesto(String flagTipoDatoRichiesto) {
		this.flagTipoDatoRichiesto = flagTipoDatoRichiesto;
	}

	public String getDatoRichiesto() {
		return datoRichiesto;
	}

	public void setDatoRichiesto(String datoRichiesto) {
		this.datoRichiesto = datoRichiesto;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
