/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbworkspace.util.BeanUtil;

public class InizializzaLineeDiFinanziamentoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	// Se true, i bottoni di creazione e modifica progetto sono visibili.
	private Boolean inserimentoModificaProgettoAbilitati;
	
	// Se true, il bottone di creazione progetto va nascosto.
	private Boolean esistonoProgettiSifAvviati;
	
	private String nomeBandoLinea;

	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}

	public java.lang.Boolean getEsistonoProgettiSifAvviati() {
		return esistonoProgettiSifAvviati;
	}

	public void setEsistonoProgettiSifAvviati(java.lang.Boolean esistonoProgettiSifAvviati) {
		this.esistonoProgettiSifAvviati = esistonoProgettiSifAvviati;
	}

	public java.lang.Boolean getInserimentoModificaProgettoAbilitati() {
		return inserimentoModificaProgettoAbilitati;
	}

	public void setInserimentoModificaProgettoAbilitati(java.lang.Boolean inserimentoModificaProgettoAbilitati) {
		this.inserimentoModificaProgettoAbilitati = inserimentoModificaProgettoAbilitati;
	}

}
