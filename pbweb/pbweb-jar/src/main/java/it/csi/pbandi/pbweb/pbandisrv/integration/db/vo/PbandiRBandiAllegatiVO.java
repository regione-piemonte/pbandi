package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

public class PbandiRBandiAllegatiVO extends GenericVO {
	
	private BigDecimal idAllegato;
	private BigDecimal progrBandoLineaIntervento;
	private String flagObbligatorio;
	private String flagDifferibile;
	
	public PbandiRBandiAllegatiVO() {};
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAllegato != null && progrBandoLineaIntervento != null) {
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
		pk.add("idAllegato");
		pk.add("progrBandoLineaIntervento");
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idAllegato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAllegato: " + temp + "\t\n");
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    temp = DataFilter.removeNull( flagObbligatorio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagObbligatorio: " + temp + "\t\n");
	    temp = DataFilter.removeNull( flagDifferibile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagDifferibile: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdAllegato() {
		return idAllegato;
	}

	public void setIdAllegato(BigDecimal idAllegato) {
		this.idAllegato = idAllegato;
	}

	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

	public String getFlagObbligatorio() {
		return flagObbligatorio;
	}

	public void setFlagObbligatorio(String flagObbligatorio) {
		this.flagObbligatorio = flagObbligatorio;
	}

	public String getFlagDifferibile() {
		return flagDifferibile;
	}

	public void setFlagDifferibile(String flagDifferibile) {
		this.flagDifferibile = flagDifferibile;
	}

}
