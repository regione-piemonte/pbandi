/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa;

import java.util.ArrayList;

import it.csi.pbandi.pbweb.dto.DatiIntegrazioneDsDTO;
import it.csi.pbandi.pbweb.dto.DocumentoAllegatoDTO;

public class AllegatiDichiarazioneDiSpesaDTO implements java.io.Serializable {
	
	static final long serialVersionUID = 1;

	private ArrayList<DocumentoAllegatoDTO> allegati;
	
	private ArrayList<DatiIntegrazioneDsDTO> integrazioni;
	
	public ArrayList<DocumentoAllegatoDTO> getAllegati() {
		return allegati;
	}
	public void setAllegati(ArrayList<DocumentoAllegatoDTO> allegati) {
		this.allegati = allegati;
	}
	public ArrayList<DatiIntegrazioneDsDTO> getIntegrazioni() {
		return integrazioni;
	}
	public void setIntegrazioni(ArrayList<DatiIntegrazioneDsDTO> integrazioni) {
		this.integrazioni = integrazioni;
	}
	
}
