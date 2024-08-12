
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiCWsboCounterVO extends GenericVO {

  	
  	private String msgid;
  	
  	private BigDecimal numAttempts;
  	
  	private String targetid;
  	
	public PbandiCWsboCounterVO() {}
  	
	public PbandiCWsboCounterVO (String msgid, String targetid) {
	
		this. msgid =  msgid;
		this. targetid =  targetid;
	}
  	
	public PbandiCWsboCounterVO (String msgid, BigDecimal numAttempts, String targetid) {
	
		this. msgid =  msgid;
		this. numAttempts =  numAttempts;
		this. targetid =  targetid;
	}
  	
  	
	public String getMsgid() {
		return msgid;
	}
	
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	
	public BigDecimal getNumAttempts() {
		return numAttempts;
	}
	
	public void setNumAttempts(BigDecimal numAttempts) {
		this.numAttempts = numAttempts;
	}
	
	public String getTargetid() {
		return targetid;
	}
	
	public void setTargetid(String targetid) {
		this.targetid = targetid;
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
		if (msgid != null && targetid != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( msgid);
	    if (!DataFilter.isEmpty(temp)) sb.append(" msgid: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numAttempts);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numAttempts: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( targetid);
	    if (!DataFilter.isEmpty(temp)) sb.append(" targetid: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("msgid");
		
			pk.add("targetid");
		
	    return pk;
	}
	
	
}
