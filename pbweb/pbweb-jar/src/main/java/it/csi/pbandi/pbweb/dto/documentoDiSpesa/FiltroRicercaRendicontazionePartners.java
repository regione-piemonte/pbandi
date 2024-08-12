package it.csi.pbandi.pbweb.dto.documentoDiSpesa;

import java.util.ArrayList;
import it.csi.pbandi.pbweb.dto.profilazione.DecodificaDTO;

public class FiltroRicercaRendicontazionePartners implements java.io.Serializable {
	
	static final long serialVersionUID = 1;

	private java.lang.Boolean visibile = null;
	private ArrayList<DecodificaDTO> opzioni = new  ArrayList<DecodificaDTO>();
	private ArrayList<DecodificaDTO> partners = new  ArrayList<DecodificaDTO>();

	public java.lang.Boolean getVisibile() {
		return visibile;
	}

	public void setVisibile(java.lang.Boolean visibile) {
		this.visibile = visibile;
	}

	public ArrayList<DecodificaDTO> getOpzioni() {
		return opzioni;
	}

	public void setOpzioni(ArrayList<DecodificaDTO> opzioni) {
		this.opzioni = opzioni;
	}

	public ArrayList<DecodificaDTO> getPartners() {
		return partners;
	}

	public void setPartners(ArrayList<DecodificaDTO> partners) {
		this.partners = partners;
	}
	
}
