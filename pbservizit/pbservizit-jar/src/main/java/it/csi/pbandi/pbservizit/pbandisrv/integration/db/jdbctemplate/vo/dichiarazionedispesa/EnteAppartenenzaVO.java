/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class EnteAppartenenzaVO extends GenericVO{
	
	private Long idEnteCompetenza = null;
	private Long idIndirizzo = null;
	private String descEnte = null;
	private String indirizzo = null;
	private String cap = null;
	private Long idComune = null;
	private String comune = null;
	private String siglaProvincia = null;
	private Long idProvincia = null;
	public Long getIdEnteCompetenza() {
		return idEnteCompetenza;
	}
	public void setIdEnteCompetenza(Long idEnteCompetenza) {
		this.idEnteCompetenza = idEnteCompetenza;
	}
	public Long getIdIndirizzo() {
		return idIndirizzo;
	}
	public void setIdIndirizzo(Long idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	public String getDescEnte() {
		return descEnte;
	}
	public void setDescEnte(String descEnte) {
		this.descEnte = descEnte;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public Long getIdComune() {
		return idComune;
	}
	public void setIdComune(Long idComune) {
		this.idComune = idComune;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getSiglaProvincia() {
		return siglaProvincia;
	}
	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}
	public Long getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(Long idProvincia) {
		this.idProvincia = idProvincia;
	}
	

	
	

}
