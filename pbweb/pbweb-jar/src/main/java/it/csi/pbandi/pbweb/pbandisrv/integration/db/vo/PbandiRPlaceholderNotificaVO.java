
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

import java.math.BigDecimal;



public class PbandiRPlaceholderNotificaVO extends GenericVO {

  	
  	private BigDecimal  idTemplateNotifica;
  	private BigDecimal  idMetadataNotifica;
  	private String  placeholder;
  	
  	
	public PbandiRPlaceholderNotificaVO() {}
  	
	public PbandiRPlaceholderNotificaVO (BigDecimal idTemplateNotifica,BigDecimal idMetadataNotifica) {
	
		this. idTemplateNotifica =  idTemplateNotifica;
		this. idMetadataNotifica =  idMetadataNotifica;
	}
  	
	public PbandiRPlaceholderNotificaVO (  BigDecimal idTemplateNotifica,  BigDecimal idMetadataNotifica, String placeholder) {
	
		this. idTemplateNotifica =  idTemplateNotifica;
		this. idMetadataNotifica =  idMetadataNotifica;
		this. placeholder =  placeholder;
	}
  	
  	
	public BigDecimal getIdMetadataNotifica() {
		return idMetadataNotifica;
	}

	public void setIdMetadataNotifica(BigDecimal idMetadataNotifica) {
		this.idMetadataNotifica = idMetadataNotifica;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public boolean isValid() {
   		return isPKValid();
	}
	
	public BigDecimal getIdTemplateNotifica() {
		return idTemplateNotifica;
	}

	public void setIdTemplateNotifica(BigDecimal idTemplateNotifica) {
		this.idTemplateNotifica = idTemplateNotifica;
	}

 

	 
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTemplateNotifica != null && idMetadataNotifica!=null) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTemplateNotifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTemplateNotifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( placeholder);
	    if (!DataFilter.isEmpty(temp)) sb.append(" placeholder: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idTemplateNotifica");
			pk.add("idMetadataNotifica");
	    return pk;
	}
	
	
}
