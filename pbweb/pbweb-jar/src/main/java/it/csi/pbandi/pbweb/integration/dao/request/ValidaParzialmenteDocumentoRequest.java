package it.csi.pbandi.pbweb.integration.dao.request;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Set;

import it.csi.pbandi.pbweb.dto.RigaValidazioneItemDTO;
import it.csi.pbandi.pbweb.util.BeanUtil;

public class ValidaParzialmenteDocumentoRequest {
	
	private Long idDichiarazioneDiSpesa;
	private Long idDocumentoDiSpesa;
	private Long idProgetto;
	private Long idBandoLinea;
	private String noteValidazione;
	
	// Campi di RigaValidazioneItemDTO che devono essere popolati: IdRigoContoEconomico, ImportoValidatoVoceDiSpesa.
	// Gli altri possono anche restare nulli.
	private ArrayList<RigaValidazioneItemDTO> righeValidazioneItem;
	
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

	public ArrayList<RigaValidazioneItemDTO> getRigheValidazioneItem() {
		return righeValidazioneItem;
	}

	public void setRigheValidazioneItem(ArrayList<RigaValidazioneItemDTO> righeValidazioneItem) {
		this.righeValidazioneItem = righeValidazioneItem;
	}

		public Long getIdBandoLinea() {
		return idBandoLinea;
	}

	public void setIdBandoLinea(Long idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+": ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				if ("righeValidazioneItem".equalsIgnoreCase(propName)) {
					ArrayList<RigaValidazioneItemDTO> lista = (ArrayList<RigaValidazioneItemDTO>) BeanUtil.getPropertyValueByName(this, propName);
					if (lista != null) {
						sb.append("\nrigheValidazioneItem:");
						for (RigaValidazioneItemDTO item : lista) {
							sb.append("\n   IdRigoContoEconomico = "+item.getIdRigoContoEconomico()+"; ImportoValidatoVoceDiSpesa = "+item.getImportoValidatoVoceDiSpesa());
						}
					} else {
						sb.append("\nrigheValidazioneItem = null");
					}
				} else {
					sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));
				}
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}
	
}
