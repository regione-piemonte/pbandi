
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;

import java.math.BigDecimal;



public class PbandiDMetadataNotificaVO extends GenericVO {

  	
  	private BigDecimal  idMetadataNotifica;
  	private String nomeParametro;
  	
  	
	public PbandiDMetadataNotificaVO() {}
  	
	public PbandiDMetadataNotificaVO ( BigDecimal idMetadataNotifica) {
	
		this. idMetadataNotifica =  idMetadataNotifica;
	}
  	
	public PbandiDMetadataNotificaVO (BigDecimal idMetadataNotifica, String nomeParametro) {
	
		this. idMetadataNotifica =  idMetadataNotifica;
		this.setNomeParametro(nomeParametro);
	}
  	
  	
	public BigDecimal getIdMetadataNotifica() {
		return idMetadataNotifica;
	}

	public void setIdMetadataNotifica(BigDecimal idMetadataNotifica) {
		this.idMetadataNotifica = idMetadataNotifica;
	}

	public String getNomeParametro() {
		return nomeParametro;
	}

	public void setNomeParametro(String nomeParametro) {
		this.nomeParametro = nomeParametro;
	}

	public boolean isValid() {
   		return isPKValid();
	}
	
 

 

	 
	public boolean isPKValid() {
		boolean isPkValid = false;
		if ( idMetadataNotifica!=null) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idMetadataNotifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idMetadataNotifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nomeParametro);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeParametro: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idMetadataNotifica");
	    return pk;
	}


	
	
}
