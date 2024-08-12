package it.csi.pbandi.pbweb.dto;

public class DatiProgettoInizializzazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private String codiceVisualizzato;
	
	private Long idTipoOperazione;
	
	private Long idProcesso;
	
	public Long getIdProcesso() {
		return idProcesso;
	}
	public void setIdProcesso(Long idProcesso) {
		this.idProcesso = idProcesso;
	}
	public Long getIdTipoOperazione() {
		return idTipoOperazione;
	}
	public void setIdTipoOperazione(Long idTipoOperazione) {
		this.idTipoOperazione = idTipoOperazione;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}

}
