/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.disimpegni;

import java.util.ArrayList;

public class Revoca {
	private ArrayList<RevocaIrregolarita> irregolarita;
	private Double importoRevocato;
	private Long idRevoca;
	private Long idMotivoRevoca;

 	public Long getIdMotivoRevoca() {
		return idMotivoRevoca;
	}

	public void setIdMotivoRevoca(Long idMotivoRevoca) {
		this.idMotivoRevoca = idMotivoRevoca;
	}

	public Double getImportoRevocato() {
		return importoRevocato;
	}

	public void setImportoRevocato(Double importoRevocato) {
		this.importoRevocato = importoRevocato;
	}

	public Long getIdRevoca() {
		return idRevoca;
	}

	public void setIdRevoca(Long idRevoca) {
		this.idRevoca = idRevoca;
	}

	public ArrayList<RevocaIrregolarita> getIrregolarita() {
		return irregolarita;
	}

	public void setIrregolarita(ArrayList<RevocaIrregolarita> irregolarita) {
		this.irregolarita = irregolarita;
	}
}
