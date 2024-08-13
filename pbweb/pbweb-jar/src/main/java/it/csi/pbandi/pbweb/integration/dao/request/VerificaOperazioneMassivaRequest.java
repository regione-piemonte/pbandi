/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.request;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Set;

import it.csi.pbandi.pbweb.dto.RigaValidazioneItemDTO;
import it.csi.pbandi.pbweb.util.BeanUtil;

public class VerificaOperazioneMassivaRequest {
	
	// "VALIDARE", "INVALIDARE", "RESPINGERE".
	private String operazione;
	
	// true = si vogliono elaborare tutti i documenti.
	// false = si vogliono elaborare solo i documenti in idDocumentiDiSpesaDaElaborare.  
	private Boolean tutti;
	
	// Elenco degli id dei documenti di spesa da elaborare; ignorato se tutti = true.
	private ArrayList<Long> idDocumentiDiSpesaDaElaborare;
	
	private Long idDichiarazioneDiSpesa;
	
	private Long idBandoLinea;

	public String getOperazione() {
		return operazione;
	}

	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}

	public Boolean getTutti() {
		return tutti;
	}

	public void setTutti(Boolean tutti) {
		this.tutti = tutti;
	}

	public Long getIdDichiarazioneDiSpesa() {
		return idDichiarazioneDiSpesa;
	}

	public void setIdDichiarazioneDiSpesa(Long idDichiarazioneDiSpesa) {
		this.idDichiarazioneDiSpesa = idDichiarazioneDiSpesa;
	}

	public Long getIdBandoLinea() {
		return idBandoLinea;
	}

	public void setIdBandoLinea(Long idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
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
