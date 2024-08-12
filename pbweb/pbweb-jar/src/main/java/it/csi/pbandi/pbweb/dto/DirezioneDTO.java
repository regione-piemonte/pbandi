package it.csi.pbandi.pbweb.dto;

import java.util.Arrays;
import java.util.List;

public class DirezioneDTO implements java.io.Serializable {
	static final long serialVersionUID = 2L;
	private Long idDirezione = null;
	private String descBreveDirezione = null;
	private List<SettoreDTO> settori = null;


	public Long getIdDirezione() {
		return idDirezione;
	}

	public void setIdDirezione(Long idDirezione) {
		this.idDirezione = idDirezione;
	}

	public String getDescBreveDirezione() {
		return descBreveDirezione;
	}

	public void setDescBreveDirezione(String descBreveDirezione) {
		this.descBreveDirezione = descBreveDirezione;
	}

	public List<SettoreDTO> getSettori() {
		return settori;
	}
	public void setSettori(List<SettoreDTO> settori) {
		this.settori = settori;
	}

	@Override
	public String toString() {
		return "DirezioneDTO{" +
				"idDirezione=" + idDirezione +
				", descBreveDirezione='" + descBreveDirezione + '\'' +
				", settori=" + Arrays.toString(settori.toArray()) +
				'}';
	}


}
