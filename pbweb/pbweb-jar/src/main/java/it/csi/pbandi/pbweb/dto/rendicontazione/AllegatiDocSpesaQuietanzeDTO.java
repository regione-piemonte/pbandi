package it.csi.pbandi.pbweb.dto.rendicontazione;

import java.util.List;

public class AllegatiDocSpesaQuietanzeDTO extends DocumentoDiSpesaSospesoDTO {

	static final long serialVersionUID = 1;

	private List<String> nomiAllegati;
	private List<AllegatiQuietanzeDTO> allegatiQuietanze;

	public List<String> getNomiAllegati() {
		return nomiAllegati;
	}

	public void setNomiAllegati(List<String> nomiAllegati) {
		this.nomiAllegati = nomiAllegati;
	}

	public List<AllegatiQuietanzeDTO> getAllegatiQuietanze() {
		return allegatiQuietanze;
	}

	public void setAllegatiQuietanze(List<AllegatiQuietanzeDTO> allegatiQuietanze) {
		this.allegatiQuietanze = allegatiQuietanze;
	}

}
