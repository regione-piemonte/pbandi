package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

public class PbandiDDettTipolAiutiVO extends GenericVO {
	
	private BigDecimal idDettTipolAiuti;
	private String descrizione;
	private BigDecimal idTipoAiuto;
	private String link;
	private String codice;
	private String flagFittizio;
	
	public PbandiDDettTipolAiutiVO() {};
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idDettTipolAiuti != null) {
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
		pk.add("idDettTipolAiuti");
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idDettTipolAiuti);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDettTipolAiuti: " + temp + "\t\n");
	    temp = DataFilter.removeNull( descrizione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrizione: " + temp + "\t\n");
	    temp = DataFilter.removeNull( idTipoAiuto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoAiuto: " + temp + "\t\n");
	    temp = DataFilter.removeNull( link);
	    if (!DataFilter.isEmpty(temp)) sb.append(" link: " + temp + "\t\n");
	    temp = DataFilter.removeNull( codice);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codice: " + temp + "\t\n");
	    temp = DataFilter.removeNull( flagFittizio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagFittizio: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdDettTipolAiuti() {
		return idDettTipolAiuti;
	}

	public void setIdDettTipolAiuti(BigDecimal idDettTipolAiuti) {
		this.idDettTipolAiuti = idDettTipolAiuti;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public BigDecimal getIdTipoAiuto() {
		return idTipoAiuto;
	}

	public void setIdTipoAiuto(BigDecimal idTipoAiuto) {
		this.idTipoAiuto = idTipoAiuto;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getFlagFittizio() {
		return flagFittizio;
	}

	public void setFlagFittizio(String flagFittizio) {
		this.flagFittizio = flagFittizio;
	}

}
