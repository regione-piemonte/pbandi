package it.csi.pbandi.pbweb.integration.dao.request;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Set;

import it.csi.pbandi.pbweb.dto.RigaValidazioneItemDTO;
import it.csi.pbandi.pbweb.util.BeanUtil;

public class OperazioneMassivaRequest {
	
	// "VALIDARE", "INVALIDARE", "RESPINGERE".
	private String operazione;
	
	// Elenco degli id dei documenti di spesa da elaborare.
	private ArrayList<Long> idDocumentiDiSpesaDaElaborare;
	
	private Long idDichiarazioneDiSpesa;

	public String getOperazione() {
		return operazione;
	}

	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}

	public Long getIdDichiarazioneDiSpesa() {
		return idDichiarazioneDiSpesa;
	}

	public void setIdDichiarazioneDiSpesa(Long idDichiarazioneDiSpesa) {
		this.idDichiarazioneDiSpesa = idDichiarazioneDiSpesa;
	}

	public ArrayList<Long> getIdDocumentiDiSpesaDaElaborare() {
		return idDocumentiDiSpesaDaElaborare;
	}

	public void setIdDocumentiDiSpesaDaElaborare(ArrayList<Long> idDocumentiDiSpesaDaElaborare) {
		this.idDocumentiDiSpesaDaElaborare = idDocumentiDiSpesaDaElaborare;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+": ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				if ("idDocumentiDiSpesaDaElaborare".equalsIgnoreCase(propName)) {
					ArrayList<Long> lista = (ArrayList<Long>) BeanUtil.getPropertyValueByName(this, propName);
					if (lista != null) {
						sb.append("\nidDocumentiDiSpesaDaElaborare:");
						for (Long item : lista) {
							sb.append("\n   idDocumentoDiSpesa = "+item);
						}
					} else {
						sb.append("\nidDocumentiDiSpesaDaElaborare = null");
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
