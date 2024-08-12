package it.csi.pbandi.pbweb.integration.dao.request;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class VerificaDichiarazioneDiSpesaRequest {
	
	private Long idProgetto;
	private Long idBandoLinea;
	private Date dataLimiteDocumentiRendicontabili;
	private Long idSoggettoBeneficiario;
	private String codiceTipoDichiarazioneDiSpesa;			// I (intermedia), F (finale), IN (integrativa).
	
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdBandoLinea() {
		return idBandoLinea;
	}
	public void setIdBandoLinea(Long idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}
	public Date getDataLimiteDocumentiRendicontabili() {
		return dataLimiteDocumentiRendicontabili;
	}
	public void setDataLimiteDocumentiRendicontabili(Date dataLimiteDocumentiRendicontabili) {
		this.dataLimiteDocumentiRendicontabili = dataLimiteDocumentiRendicontabili;
	}
	public Long getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	public void setIdSoggettoBeneficiario(Long idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public String getCodiceTipoDichiarazioneDiSpesa() {
		return codiceTipoDichiarazioneDiSpesa;
	}
	public void setCodiceTipoDichiarazioneDiSpesa(String codiceTipoDichiarazioneDiSpesa) {
		this.codiceTipoDichiarazioneDiSpesa = codiceTipoDichiarazioneDiSpesa;
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
