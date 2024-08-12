package it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa;

public class CodiceDescrizioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private String codice = null;

	public void setCodice(String val) {
		codice = val;
	}

	public String getCodice() {
		return codice;
	}

	private String descrizione = null;

	public void setDescrizione(String val) {
		descrizione = val;
	}

	public String getDescrizione() {
		return descrizione;
	}

	@Override
	public String toString() {
		return "CodiceDescrizioneDTO{" +
				"codice='" + codice + '\'' +
				", descrizione='" + descrizione + '\'' +
				'}';
	}
}