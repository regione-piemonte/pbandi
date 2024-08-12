package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

public class PbandiRBandiAtecoVO extends GenericVO {
	
	private BigDecimal idBando;
	private BigDecimal idAteco;
	
	public PbandiRBandiAtecoVO() {};
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idBando != null && idAteco != null) {
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
		pk.add("idBando");
		pk.add("idAteco");
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBando: " + temp + "\t\n");
	    temp = DataFilter.removeNull( idAteco);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAteco: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdBando() {
		return idBando;
	}

	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}

	public BigDecimal getIdAteco() {
		return idAteco;
	}

	public void setIdAteco(BigDecimal idAteco) {
		this.idAteco = idAteco;
	}

}
