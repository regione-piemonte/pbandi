
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRDocuIndexTipoStatoVO extends GenericVO {

  	
  	private BigDecimal idTipoDocumentoIndex;
  	
  	private BigDecimal idStatoTipoDocIndex;
  	
  	private BigDecimal idDocumentoIndex;
  	
	public PbandiRDocuIndexTipoStatoVO() {}
  	
	public PbandiRDocuIndexTipoStatoVO (BigDecimal idTipoDocumentoIndex, BigDecimal idStatoTipoDocIndex, BigDecimal idDocumentoIndex) {
	
		this. idTipoDocumentoIndex =  idTipoDocumentoIndex;
		this. idStatoTipoDocIndex =  idStatoTipoDocIndex;
		this. idDocumentoIndex =  idDocumentoIndex;
	}

	public BigDecimal getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}
	
	public void setIdTipoDocumentoIndex(BigDecimal idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}
	
	public BigDecimal getIdStatoTipoDocIndex() {
		return idStatoTipoDocIndex;
	}
	
	public void setIdStatoTipoDocIndex(BigDecimal idStatoTipoDocIndex) {
		this.idStatoTipoDocIndex = idStatoTipoDocIndex;
	}
	
	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	
	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
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
		if (idTipoDocumentoIndex != null && idStatoTipoDocIndex != null && idDocumentoIndex != null ) {
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
	    
	    temp = DataFilter.removeNull( idStatoTipoDocIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoTipoDocIndex: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDocumentoIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDocumentoIndex: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoDocumentoIndex");
		
			pk.add("idStatoTipoDocIndex");
		
			pk.add("idDocumentoIndex");
		
	    return pk;
	}
	
	
}
