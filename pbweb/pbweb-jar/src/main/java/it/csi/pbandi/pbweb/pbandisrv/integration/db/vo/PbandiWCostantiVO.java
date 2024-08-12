package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

import java.math.BigDecimal;

public class PbandiWCostantiVO extends GenericVO {
	
	// CDU-14 V04: nuova classe
	
	private String attributo;
	
	private String valore;
	
	private String normaIncentivazione;
	
	public String getAttributo() {
		return attributo;
	}

	public void setAttributo(String attributo) {
		this.attributo = attributo;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	public String getNormaIncentivazione() {
		return normaIncentivazione;
	}

	public void setNormaIncentivazione(String normaIncentivazione) {
		this.normaIncentivazione = normaIncentivazione;
	}
	
	public PbandiWCostantiVO () {}
	
	public PbandiWCostantiVO (String attributo, String valore, String normaIncentivazione) {		
		this.attributo = attributo;
		this.valore = valore;
		this.normaIncentivazione = normaIncentivazione;
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
		if (attributo != null && valore != null && normaIncentivazione != null) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull(attributo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" attributo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull(valore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" valore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull(normaIncentivazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" normaIncentivazione: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		pk.add("attributo");
		pk.add("valore");
		pk.add("normaIncentivazione");
	    return pk;
	}
	

}
