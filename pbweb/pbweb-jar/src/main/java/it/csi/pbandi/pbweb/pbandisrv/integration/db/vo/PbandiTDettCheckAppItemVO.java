package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTDettCheckAppItemVO extends GenericVO {
 
  	
  	private BigDecimal idDettCheckAppalti;
  	private BigDecimal idDettCheckAppItem;
  	private String nomeCampoEdit;
  	private String valoreEdit;
  	
	public PbandiTDettCheckAppItemVO() {}
  	
	public PbandiTDettCheckAppItemVO (BigDecimal idDettCheckAppalti) {
	
		this.setIdDettCheckAppalti(idDettCheckAppalti);
	}
  	
	public PbandiTDettCheckAppItemVO (String nomeCampoEdit, String valoreEdit, BigDecimal idDettCheckAppItem, BigDecimal idDettCheckAppalti) {
	
		this.setNomeCampoEdit(nomeCampoEdit);
		this.setValoreEdit(valoreEdit);
		this.setIdDettCheckAppItem(idDettCheckAppItem);
		this.setIdDettCheckAppalti(idDettCheckAppalti);
	}
  	
  	
	 
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && getIdDettCheckAppItem() != null && getIdDettCheckAppalti() != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (getIdDettCheckAppItem() != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    
	    temp = DataFilter.removeNull( getValoreEdit());
	    if (!DataFilter.isEmpty(temp)) sb.append(" valoreEdit: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( getIdDettCheckAppItem());
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDettCheckAppItem: " + temp + "\t\n");
	    
	    
	    temp = DataFilter.removeNull( getIdDettCheckAppalti());
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDettCheckAppalti: " + temp + "\t\n");
	    
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idDettCheckAppItem");
		
	    return pk;
	}

	public String getNomeCampoEdit() {
		return nomeCampoEdit;
	}

	public void setNomeCampoEdit(String nomeCampoEdit) {
		this.nomeCampoEdit = nomeCampoEdit;
	}

	public BigDecimal getIdDettCheckAppItem() {
		return idDettCheckAppItem;
	}

	public void setIdDettCheckAppItem(BigDecimal idDettCheckAppItem) {
		this.idDettCheckAppItem = idDettCheckAppItem;
	}

	public BigDecimal getIdDettCheckAppalti() {
		return idDettCheckAppalti;
	}

	public void setIdDettCheckAppalti(BigDecimal idDettCheckAppalti) {
		this.idDettCheckAppalti = idDettCheckAppalti;
	}

	public String getValoreEdit() {
		return valoreEdit;
	}

	public void setValoreEdit(String valoreEdit) {
		this.valoreEdit = valoreEdit;
	}
	
	
}
