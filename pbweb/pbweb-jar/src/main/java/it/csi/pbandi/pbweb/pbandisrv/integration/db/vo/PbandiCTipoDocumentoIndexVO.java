
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiCTipoDocumentoIndexVO extends GenericVO {

  	
  	private BigDecimal idTipoDocumentoIndex;
  	
  	private String descBreveTipoDocIndex;
  	
  	private String descTipoDocIndex;
  	
  	private String flagFirmabile;
  	
  	private String flagUploadable;
  	
	public PbandiCTipoDocumentoIndexVO() {}
  	
	public PbandiCTipoDocumentoIndexVO (BigDecimal idTipoDocumentoIndex) {
	
		this. idTipoDocumentoIndex =  idTipoDocumentoIndex;
	}
  	
	public PbandiCTipoDocumentoIndexVO (BigDecimal idTipoDocumentoIndex, String descBreveTipoDocIndex, String descTipoDocIndex ,  String flagFirmabile, String flagUploadable) {
		this. idTipoDocumentoIndex =  idTipoDocumentoIndex;
		this. descBreveTipoDocIndex =  descBreveTipoDocIndex;
		this. descTipoDocIndex =  descTipoDocIndex;
		this. flagFirmabile = flagFirmabile;
		this. flagUploadable = flagUploadable;
	}
  	  	
	public BigDecimal getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}
	
	public void setIdTipoDocumentoIndex(BigDecimal idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}
	
	public String getFlagFirmabile() {
		return flagFirmabile;
	}

	public void setFlagFirmabile(String flagFirmabile) {
		this.flagFirmabile = flagFirmabile;
	}

	public String getDescBreveTipoDocIndex() {
		return descBreveTipoDocIndex;
	}
	
	public void setDescBreveTipoDocIndex(String descBreveTipoDocIndex) {
		this.descBreveTipoDocIndex = descBreveTipoDocIndex;
	}
	
	public String getDescTipoDocIndex() {
		return descTipoDocIndex;
	}
	
	public void setDescTipoDocIndex(String descTipoDocIndex) {
		this.descTipoDocIndex = descTipoDocIndex;
	}

	public String getFlagUploadable() {
		return flagUploadable;
	}

	public void setFlagUploadable(String flagUploadable) {
		this.flagUploadable = flagUploadable;
	}

	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveTipoDocIndex != null && descTipoDocIndex != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoDocumentoIndex != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoDocumentoIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoDocumentoIndex: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoDocIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoDocIndex: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoDocIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoDocIndex: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagFirmabile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagFirmabile: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagUploadable);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagUploadable: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idTipoDocumentoIndex");
		
	    return pk;
	}

}
