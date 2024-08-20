/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

import java.util.List;

public class DatiQteDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String estremiAttoApprovazione;
	private String estremiAttoApprovazioneCert;
	private Long idColonnaQtes;
	private List<NumberDescBreve> importiFonti;
	private List<CheckDescBreve> tipiIntervento;
	private List<NumberDescBreve> altriValori;

	public String getEstremiAttoApprovazione() {
		return estremiAttoApprovazione;
	}

	public void setEstremiAttoApprovazione(String estremiAttoApprovazione) {
		this.estremiAttoApprovazione = estremiAttoApprovazione;
	}

	public Long getIdColonnaQtes() {
		return idColonnaQtes;
	}

	public void setIdColonnaQtes(Long idColonnaQtes) {
		this.idColonnaQtes = idColonnaQtes;
	}

	public String getEstremiAttoApprovazioneCert() {
		return estremiAttoApprovazioneCert;
	}

	public void setEstremiAttoApprovazioneCert(String estremiAttoApprovazioneCert) {
		this.estremiAttoApprovazioneCert = estremiAttoApprovazioneCert;
	}

	public List<NumberDescBreve> getImportiFonti() {
		return importiFonti;
	}

	public void setImportiFonti(List<NumberDescBreve> importiFonti) {
		this.importiFonti = importiFonti;
	}

	public List<CheckDescBreve> getTipiIntervento() {
		return tipiIntervento;
	}

	public void setTipiIntervento(List<CheckDescBreve> tipiIntervento) {
		this.tipiIntervento = tipiIntervento;
	}

	public List<NumberDescBreve> getAltriValori() {
		return altriValori;
	}

	public void setAltriValori(List<NumberDescBreve> altriValori) {
		this.altriValori = altriValori;
	}

}
