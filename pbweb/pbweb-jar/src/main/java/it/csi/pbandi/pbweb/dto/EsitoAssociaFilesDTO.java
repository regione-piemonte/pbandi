/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto;

import java.util.ArrayList;

public class EsitoAssociaFilesDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	// Elenco dei file che sono stati associati con successo.
	private ArrayList<Long> elencoIdDocIndexFilesAssociati = new ArrayList<Long>();
	
	// Elenco dei file che non è stato possibile associare.
	private ArrayList<Long> elencoIdDocIndexFilesNonAssociati = new ArrayList<Long>();
	
	public ArrayList<Long> getElencoIdDocIndexFilesAssociati() {
		return elencoIdDocIndexFilesAssociati;
	}
	public void setElencoIdDocIndexFilesAssociati(ArrayList<Long> elencoIdDocIndexFilesAssociati) {
		this.elencoIdDocIndexFilesAssociati = elencoIdDocIndexFilesAssociati;
	}
	public ArrayList<Long> getElencoIdDocIndexFilesNonAssociati() {
		return elencoIdDocIndexFilesNonAssociati;
	}
	public void setElencoIdDocIndexFilesNonAssociati(ArrayList<Long> elencoIdDocIndexFilesNonAssociati) {
		this.elencoIdDocIndexFilesNonAssociati = elencoIdDocIndexFilesNonAssociati;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			
			String idAssociati = "";
			if (this.elencoIdDocIndexFilesAssociati != null) {
				for (Long id : this.elencoIdDocIndexFilesAssociati) {
					idAssociati = (idAssociati.length() == 0) ? id.toString() : " - "+id.toString();
				}
			}
			
			String idNonAssociati = "";
			if (this.elencoIdDocIndexFilesNonAssociati != null) {
				for (Long id : this.elencoIdDocIndexFilesNonAssociati) {
					idNonAssociati = (idNonAssociati.length() == 0) ? id.toString() : " - "+id.toString();
				}
			}
			
			sb.append("\nEsitoAssociaFilesDTO: IdDocIndexFilesAssociati = "+this.elencoIdDocIndexFilesAssociati+"; IdDocIndexFilesNonAssociati = "+this.elencoIdDocIndexFilesNonAssociati);

		} catch (Exception e) {
		}
		return sb.toString();
	}

}
