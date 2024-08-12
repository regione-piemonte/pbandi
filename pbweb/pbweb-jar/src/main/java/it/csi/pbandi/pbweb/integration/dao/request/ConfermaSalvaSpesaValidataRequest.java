package it.csi.pbandi.pbweb.integration.dao.request;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Set;

import it.csi.pbandi.pbweb.dto.RigaValidazioneItemDTO;
import it.csi.pbandi.pbweb.util.BeanUtil;

public class ConfermaSalvaSpesaValidataRequest {

	private Long idDichiarazioneDiSpesa;
	private Long idDocumentoDiSpesa;
	private Long idProgetto;
	private ArrayList<RigaValidazioneItemDTO> pagamentiAssociati;
	private String noteValidazione;

	public ArrayList<RigaValidazioneItemDTO> getPagamentiAssociati() {
		return pagamentiAssociati;
	}

	public void setPagamentiAssociati(ArrayList<RigaValidazioneItemDTO> pagamentiAssociati) {
		this.pagamentiAssociati = pagamentiAssociati;
	}

	public Long getIdDichiarazioneDiSpesa() {
		return idDichiarazioneDiSpesa;
	}

	public void setIdDichiarazioneDiSpesa(Long idDichiarazioneDiSpesa) {
		this.idDichiarazioneDiSpesa = idDichiarazioneDiSpesa;
	}

	public Long getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}

	public void setIdDocumentoDiSpesa(Long idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public String getNoteValidazione() {
		return noteValidazione;
	}

	public void setNoteValidazione(String noteValidazione) {
		this.noteValidazione = noteValidazione;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n" + this.getClass().getName() + ": ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				sb.append("\n" + propName + " = " + BeanUtil.getPropertyValueByName(this, propName));
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}

}
