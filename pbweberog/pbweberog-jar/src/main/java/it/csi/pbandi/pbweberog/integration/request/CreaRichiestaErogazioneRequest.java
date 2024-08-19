/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import java.io.Serializable;
import java.util.List;

import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RappresentanteLegaleDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RichiestaErogazioneDTO;
import it.csi.pbandi.pbweberog.dto.erogazione.AffidServtecArDTO;

public class CreaRichiestaErogazioneRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idProgetto;
	private RichiestaErogazioneDTO richiesta;
	private RappresentanteLegaleDTO rappresentante;
	private Long idDelegato;
	private List<AffidServtecArDTO> affidamentiServiziLavori;

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public RichiestaErogazioneDTO getRichiesta() {
		return richiesta;
	}

	public void setRichiesta(RichiestaErogazioneDTO richiesta) {
		this.richiesta = richiesta;
	}

	public RappresentanteLegaleDTO getRappresentante() {
		return rappresentante;
	}

	public void setRappresentante(RappresentanteLegaleDTO rappresentante) {
		this.rappresentante = rappresentante;
	}

	public Long getIdDelegato() {
		return idDelegato;
	}

	public void setIdDelegato(Long idDelegato) {
		this.idDelegato = idDelegato;
	}

	public List<AffidServtecArDTO> getAffidamentiServiziLavori() {
		return affidamentiServiziLavori;
	}

	public void setAffidamentiServiziLavori(List<AffidServtecArDTO> affidamentiServiziLavori) {
		this.affidamentiServiziLavori = affidamentiServiziLavori;
	}

}
