package it.csi.pbandi.pbweb.integration.vo;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.integration.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbweb.util.BeanUtil;

public class ProgettoBandoLineaVO extends PbandiTProgettoVO {
	
	private Long idBandoLinea;
	private Long idProcesso;
	private String versioneProcesso;
	private String descrizioneBando;
	private String titoloBando;
	private String beneficiario;
	
	public Long getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(Long idProcesso) {
		this.idProcesso = idProcesso;
	}

	public void setIdBandoLinea(Long idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}

	public Long getIdBandoLinea() {
		return idBandoLinea;
	}

	public void setVersioneProcesso(String versioneProcesso) {
		this.versioneProcesso = versioneProcesso;
	}

	public String getVersioneProcesso() {
		return versioneProcesso;
	}

	public void setDescrizioneBando(String descrizioneBando) {
		this.descrizioneBando = descrizioneBando;
	}

	public String getDescrizioneBando() {
		return descrizioneBando;
	}

	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}

	public String getBeneficiario() {
		return beneficiario;
	}

	public void setTitoloBando(String titoloBando) {
		this.titoloBando = titoloBando;
	}

	public String getTitoloBando() {
		return titoloBando;
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
