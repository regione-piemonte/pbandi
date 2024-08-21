/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.cronoprogramma;

public class DatiCronoprogramma implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idTipoOperazione;
	private String descTipoOperazione;
	private Long idIter;
	private String dtPresentazioneDomanda;
	private String dtConcessioneComitato;
	
	
	public void setIdTipoOperazione(Long val) {
		idTipoOperazione = val;
	}

	public Long getIdTipoOperazione() {
		return idTipoOperazione;
	}



	public void setDescTipoOperazione(String val) {
		descTipoOperazione = val;
	}

	public String getDescTipoOperazione() {
		return descTipoOperazione;
	}

	

	public void setIdIter(Long val) {
		idIter = val;
	}

	public Long getIdIter() {
		return idIter;
	}

	/// Field [descIter]
	private String descIter;

	public void setDescIter(String val) {
		descIter = val;
	}

	public String getDescIter() {
		return descIter;
	}



	public void setDtPresentazioneDomanda(String val) {
		dtPresentazioneDomanda = val;
	}

	public String getDtPresentazioneDomanda() {
		return dtPresentazioneDomanda;
	}



	public void setDtConcessioneComitato(String val) {
		dtConcessioneComitato = val;
	}

	public String getDtConcessioneComitato() {
		return dtConcessioneComitato;
	}

	private String codiceFaseFinaleObbligatoria;

	public void setCodiceFaseFinaleObbligatoria(String val) {
		codiceFaseFinaleObbligatoria = val;
	}

	public String getCodiceFaseFinaleObbligatoria() {
		return codiceFaseFinaleObbligatoria;
	}


	
	public DatiCronoprogramma() {
		super();
	}

	public String toString() {

		return super.toString();
	}
}
