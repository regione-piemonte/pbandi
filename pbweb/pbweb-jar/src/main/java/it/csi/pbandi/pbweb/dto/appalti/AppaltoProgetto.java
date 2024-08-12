package it.csi.pbandi.pbweb.dto.appalti;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class AppaltoProgetto implements java.io.Serializable {

	private Long idProgetto = null;
	private Long idAppalto = null;
	private Boolean associato = false;
	private String descrizione = null;

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public Long getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(Long idAppalto) {
		this.idAppalto = idAppalto;
	}

	public Boolean getAssociato() {
		return associato;
	}

	public void setAssociato(Boolean associato) {
		this.associato = associato;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public AppaltoProgetto() { }

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
