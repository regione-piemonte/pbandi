/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;
import java.math.BigDecimal;

public class PbandiDTipolBeneficiariVO extends GenericVO {
	
	private BigDecimal idTipolBeneficiario;
	private String codice;
	private String descrizione;
	private BigDecimal flagPubblicoPrivato;
	private String codStereotipo;
	
	public PbandiDTipolBeneficiariVO() {};
	
	public PbandiDTipolBeneficiariVO (BigDecimal idTipolBeneficiario) {
		
		this.idTipolBeneficiario = idTipolBeneficiario;
	}
  	
	public PbandiDTipolBeneficiariVO (BigDecimal idTipolBeneficiario, String codice, String descrizione, BigDecimal flagPubblicoprivato, String codStereotipo) {	
		this.idTipolBeneficiario = idTipolBeneficiario;
		this.codice = codice;
		this.descrizione = descrizione;
		this.flagPubblicoPrivato = flagPubblicoprivato;
		this.codStereotipo = codStereotipo;
	}
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipolBeneficiario != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codice != null && descrizione != null && flagPubblicoPrivato != null && codStereotipo != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk = new java.util.ArrayList<String>();
		pk.add("idTipolBeneficiario");		
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipolBeneficiario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipolBeneficiario: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codice);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codice: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrizione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagPubblicoPrivato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagPubblicoprivato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codStereotipo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codStereotipo: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public BigDecimal getIdTipolBeneficiario() {
		return idTipolBeneficiario;
	}
	public void setIdTipolBeneficiario(BigDecimal idTipolBeneficiario) {
		this.idTipolBeneficiario = idTipolBeneficiario;
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

	public BigDecimal getFlagPubblicoPrivato() {
		return flagPubblicoPrivato;
	}

	public void setFlagPubblicoPrivato(BigDecimal flagPubblicoPrivato) {
		this.flagPubblicoPrivato = flagPubblicoPrivato;
	}

	public String getCodStereotipo() {
		return codStereotipo;
	}
	public void setCodStereotipo(String codStereotipo) {
		this.codStereotipo = codStereotipo;
	}

}
