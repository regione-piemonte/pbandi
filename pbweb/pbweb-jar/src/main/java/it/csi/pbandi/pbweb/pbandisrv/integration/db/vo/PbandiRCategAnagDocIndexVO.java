
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

public class PbandiRCategAnagDocIndexVO extends GenericVO {

	private BigDecimal idCategAnagrafica;
  	
	private BigDecimal idDocumentoIndex;
  	
  	public BigDecimal getIdCategAnagrafica() {
		return idCategAnagrafica;
	}

	public void setIdCategAnagrafica(BigDecimal idCategAnagrafica) {
		this.idCategAnagrafica = idCategAnagrafica;
	}
	
 	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}

	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}

	public PbandiRCategAnagDocIndexVO() {}
  	
	public PbandiRCategAnagDocIndexVO (BigDecimal idCategAnagrafica) {	
		this. idCategAnagrafica =  idCategAnagrafica;
	}
  	
	public PbandiRCategAnagDocIndexVO (BigDecimal idCategAnagrafica, BigDecimal idDocumentoIndex) {	
		this. idCategAnagrafica =  idCategAnagrafica;
		this. idDocumentoIndex =  idDocumentoIndex;
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
		if (idCategAnagrafica != null && idDocumentoIndex != null) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idCategAnagrafica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCategAnagrafica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDocumentoIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDocumentoIndex: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();		
			pk.add("idCategAnagrafica");
			pk.add("idDocumentoIndex");		
	    return pk;
	}
	
	
}
