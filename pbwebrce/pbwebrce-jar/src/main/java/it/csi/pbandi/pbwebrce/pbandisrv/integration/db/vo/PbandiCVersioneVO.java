/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiCVersioneVO extends GenericVO {

  	
  	private String versioneDb;
  	
	public PbandiCVersioneVO() {}
  	
  	
	public PbandiCVersioneVO (String versioneDb) {
	
		this. versioneDb =  versioneDb;
	}
  	
  	
	public String getVersioneDb() {
		return versioneDb;
	}
	
	public void setVersioneDb(String versioneDb) {
		this.versioneDb = versioneDb;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && versioneDb != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = true;

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( versioneDb);
	    if (!DataFilter.isEmpty(temp)) sb.append(" versioneDb: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
	    return pk;
	}
	
	
}
