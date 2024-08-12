package it.csi.pbandi.pbweb.dto;

public class EsitoImportoSaldoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Boolean esito;
	private String messaggio;
	private Double importoSpeso;
	private Double sommaErogato;

	public Boolean getEsito() {
		return esito;
	}

	public void setEsito(Boolean esito) {
		this.esito = esito;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	public Double getImportoSpeso() {
		return importoSpeso;
	}

	public void setImportoSpeso(Double importoSpeso) {
		this.importoSpeso = importoSpeso;
	}

	public Double getSommaErogato() {
		return sommaErogato;
	}

	public void setSommaErogato(Double sommaErogato) {
		this.sommaErogato = sommaErogato;
	}

}
