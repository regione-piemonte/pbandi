/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.response;

import java.io.Serializable;
import java.util.List;

import it.csi.pbandi.pbservizit.pbandisrv.dto.recupero.EsitoSalvaRecuperoDTO;
import it.csi.pbandi.pbweberog.dto.recupero.RigaRecuperoItem;

public class ResponseRecuperaTutto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<RigaRecuperoItem> recuperi;
	private EsitoSalvaRecuperoDTO esito;
	public List<RigaRecuperoItem> getRecuperi() {
		return recuperi;
	}
	public void setRecuperi(List<RigaRecuperoItem> recuperi) {
		this.recuperi = recuperi;
	}
	public EsitoSalvaRecuperoDTO getEsito() {
		return esito;
	}
	public void setEsito(EsitoSalvaRecuperoDTO esito) {
		this.esito = esito;
	}
	
	
	
}
