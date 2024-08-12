package it.csi.pbandi.pbweb.dto;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class EsitoOperazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Boolean esito = null;
	private java.util.ArrayList<String> messaggi = new java.util.ArrayList<String>();
	private java.lang.Long id = null;
	
	public java.lang.Long getId() {
		return id;
	}
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	public java.lang.Boolean getEsito() {
		return esito;
	}
	public void setEsito(java.lang.Boolean esito) {
		this.esito = esito;
	}	
	public java.util.ArrayList<String> getMessaggi() {
		return messaggi;
	}
	public void setMessaggi(java.util.ArrayList<String> messaggi) {
		this.messaggi = messaggi;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nEsitoOperazioneDTO: ");
		try {
			sb.append("\n esito = "+esito);
			sb.append("\n id = "+id);
			if (messaggi == null) {
				sb.append("\n num messaggi = 0");
			} else {
				sb.append("\n num messaggi = "+messaggi.size());
				for (String s : messaggi)
					sb.append("\n msg = "+s);
			}
		} catch (Exception e) {
		}
		return sb.toString();
	}

}
