package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

public class ModelloBandoLineaVO  extends GenericVO {
	
	// CDU-14 V04: nuova classe
	
	private String idTipoDocumentoIndex;
	
	private String descTipoDocIndex;
	
	private String descBreveTipoDocIndex;
	
	private Long progrBandoLineaIntervento;

	public Long getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(Long progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

	public String getDescBreveTipoDocIndex() {
		return descBreveTipoDocIndex;
	}

	public void setDescBreveTipoDocIndex(String descBreveTipoDocIndex) {
		this.descBreveTipoDocIndex = descBreveTipoDocIndex;
	}

	public String getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}

	public void setIdTipoDocumentoIndex(String idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}

	public String getDescTipoDocIndex() {
		return descTipoDocIndex;
	}

	public void setDescTipoDocIndex(String descTipoDocIndex) {
		this.descTipoDocIndex = descTipoDocIndex;
	}
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoDocumentoIndex != null) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull(idTipoDocumentoIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoDocumentoIndex: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull(descTipoDocIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoDocIndex: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull(descBreveTipoDocIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoDocIndex: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull(progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoDocumentoIndex");
		
	    return pk;
	}

}
