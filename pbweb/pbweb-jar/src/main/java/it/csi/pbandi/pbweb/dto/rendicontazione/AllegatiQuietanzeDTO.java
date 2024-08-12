package it.csi.pbandi.pbweb.dto.rendicontazione;

import java.util.List;

public class AllegatiQuietanzeDTO extends QuietanzaDTO {

	static final long serialVersionUID = 1;

	private List<String> nomiAllegati;

	public List<String> getNomiAllegati() {
		return nomiAllegati;
	}

	public void setNomiAllegati(List<String> nomiAllegati) {
		this.nomiAllegati = nomiAllegati;
	}

}
