package it.csi.pbandi.pbweb.dto.validazioneRendicontazione;


public class EsitoDichiarazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private int id;
	private String esito;
	private String esitoBreve;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public String getEsitoBreve() {
		return esitoBreve;
	}
	public void setEsitoBreve(String esitoBreve) {
		this.esitoBreve = esitoBreve;
	}
	
	
	
}
