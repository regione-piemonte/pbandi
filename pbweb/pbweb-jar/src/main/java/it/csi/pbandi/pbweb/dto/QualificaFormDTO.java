package it.csi.pbandi.pbweb.dto;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class QualificaFormDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Long progrFornitoreQualifica;
	private java.lang.Long idFornitore = null;
	private java.lang.Long idQualifica = null;
	private java.lang.Double costoOrario = null;
	private java.lang.String noteQualifica = null;
	
	public java.lang.Long getProgrFornitoreQualifica() {
		return progrFornitoreQualifica;
	}
	public void setProgrFornitoreQualifica(java.lang.Long progrFornitoreQualifica) {
		this.progrFornitoreQualifica = progrFornitoreQualifica;
	}
	public java.lang.Long getIdFornitore() {
		return idFornitore;
	}
	public void setIdFornitore(java.lang.Long idFornitore) {
		this.idFornitore = idFornitore;
	}
	public java.lang.Long getIdQualifica() {
		return idQualifica;
	}
	public void setIdQualifica(java.lang.Long idQualifica) {
		this.idQualifica = idQualifica;
	}
	public java.lang.Double getCostoOrario() {
		return costoOrario;
	}
	public void setCostoOrario(java.lang.Double costoOrario) {
		this.costoOrario = costoOrario;
	}
	public java.lang.String getNoteQualifica() {
		return noteQualifica;
	}
	public void setNoteQualifica(java.lang.String noteQualifica) {
		this.noteQualifica = noteQualifica;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nQualificaFormDTO: ");
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
