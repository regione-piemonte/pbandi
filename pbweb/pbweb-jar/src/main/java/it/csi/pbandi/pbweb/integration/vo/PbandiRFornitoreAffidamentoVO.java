package it.csi.pbandi.pbweb.integration.vo;

import java.beans.IntrospectionException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class PbandiRFornitoreAffidamentoVO {
	
	private BigDecimal idFornitore;
	private BigDecimal idAppalto;
	private BigDecimal idTipoPercettore;
	private Date dtInvioVerificaAffidamento;
	private String flgInvioVerificaAffidamento;
	public BigDecimal getIdFornitore() {
		return idFornitore;
	}
	public void setIdFornitore(BigDecimal idFornitore) {
		this.idFornitore = idFornitore;
	}
	public BigDecimal getIdAppalto() {
		return idAppalto;
	}
	public void setIdAppalto(BigDecimal idAppalto) {
		this.idAppalto = idAppalto;
	}
	public BigDecimal getIdTipoPercettore() {
		return idTipoPercettore;
	}
	public void setIdTipoPercettore(BigDecimal idTipoPercettore) {
		this.idTipoPercettore = idTipoPercettore;
	}
	public Date getDtInvioVerificaAffidamento() {
		return dtInvioVerificaAffidamento;
	}
	public void setDtInvioVerificaAffidamento(Date dtInvioVerificaAffidamento) {
		this.dtInvioVerificaAffidamento = dtInvioVerificaAffidamento;
	}
	public String getFlgInvioVerificaAffidamento() {
		return flgInvioVerificaAffidamento;
	}
	public void setFlgInvioVerificaAffidamento(String flgInvioVerificaAffidamento) {
		this.flgInvioVerificaAffidamento = flgInvioVerificaAffidamento;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+": ");
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
