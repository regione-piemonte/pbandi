/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.boxAttivitaIstruttore;

import java.util.List;

public class InitBoxSaldoStralcioVO {
	
	// Per le dropDown della dialog
	private List<String> listaTipiSaldoStralcio;
	private List<String> listaEsiti;
	private List<String> listaRecuperi;
	
	public InitBoxSaldoStralcioVO(List<String> listaTipiSaldoStralcio, List<String> listaEsiti,
			List<String> listaRecuperi) {
		this.listaTipiSaldoStralcio = listaTipiSaldoStralcio;
		this.listaEsiti = listaEsiti;
		this.listaRecuperi = listaRecuperi;
	}

	public InitBoxSaldoStralcioVO() {
	}

	public List<String> getListaTipiSaldoStralcio() {
		return listaTipiSaldoStralcio;
	}

	public void setListaTipiSaldoStralcio(List<String> listaTipiSaldoStralcio) {
		this.listaTipiSaldoStralcio = listaTipiSaldoStralcio;
	}

	public List<String> getListaEsiti() {
		return listaEsiti;
	}

	public void setListaEsiti(List<String> listaEsiti) {
		this.listaEsiti = listaEsiti;
	}

	public List<String> getListaRecuperi() {
		return listaRecuperi;
	}

	public void setListaRecuperi(List<String> listaRecuperi) {
		this.listaRecuperi = listaRecuperi;
	}

	@Override
	public String toString() {
		return "InitBoxSaldoStralcioVO [listaTipiSaldoStralcio=" + listaTipiSaldoStralcio + ", listaEsiti=" + listaEsiti
				+ ", listaRecuperi=" + listaRecuperi + "]";
	}
	
	
	
	
	
	
	

}
