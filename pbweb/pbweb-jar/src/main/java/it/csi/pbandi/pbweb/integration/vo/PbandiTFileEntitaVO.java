
package it.csi.pbandi.pbweb.integration.vo;

import java.beans.IntrospectionException;
import java.math.*;
import java.sql.Date;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class PbandiTFileEntitaVO {
  	
	private BigDecimal idFile;
	
	private BigDecimal idFileEntita;	
	
	private BigDecimal idEntita;
	
	private BigDecimal idProgetto;
	
	private Date dtEntita;
	
	private String flagEntita;
	
	public BigDecimal getIdFile() {
		return idFile;
	}
	public void setIdFile(BigDecimal idFile) {
		this.idFile = idFile;
	}
	public BigDecimal getIdFileEntita() {
		return idFileEntita;
	}
	public void setIdFileEntita(BigDecimal idFileEntita) {
		this.idFileEntita = idFileEntita;
	}
	public BigDecimal getIdEntita() {
		return idEntita;
	}
	public void setIdEntita(BigDecimal idEntita) {
		this.idEntita = idEntita;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Date getDtEntita() {
		return dtEntita;
	}
	public void setDtEntita(Date dtEntita) {
		this.dtEntita = dtEntita;
	}
	public String getFlagEntita() {
		return flagEntita;
	}
	public void setFlagEntita(String flagEntita) {
		this.flagEntita = flagEntita;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+" : ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}		
	
	
}
