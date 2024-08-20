/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.request;

import java.util.ArrayList;

import it.csi.pbandi.pbwebrce.dto.ModalitaAgevolazione;

public class SalvaRichiestaContoEconomicoRequest {
	
	private Long idProgetto;
	private Long idContoEconomico;
	private Long idSoggettoBeneficiario;
	private ArrayList<ModalitaAgevolazione> listaModalitaAgevolazione;
	private String note;
	private Long idRapprensentanteLegale;
	private Double importoFinanziamentoRichiesto;
	
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdContoEconomico() {
		return idContoEconomico;
	}
	public void setIdContoEconomico(Long idContoEconomico) {
		this.idContoEconomico = idContoEconomico;
	}
	public Long getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	public void setIdSoggettoBeneficiario(Long idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public ArrayList<ModalitaAgevolazione> getListaModalitaAgevolazione() {
		return listaModalitaAgevolazione;
	}
	public void setListaModalitaAgevolazione(ArrayList<ModalitaAgevolazione> listaModalitaAgevolazione) {
		this.listaModalitaAgevolazione = listaModalitaAgevolazione;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Long getIdRapprensentanteLegale() {
		return idRapprensentanteLegale;
	}
	public void setIdRapprensentanteLegale(Long idRapprensentanteLegale) {
		this.idRapprensentanteLegale = idRapprensentanteLegale;
	}
	public Double getImportoFinanziamentoRichiesto() {
		return importoFinanziamentoRichiesto;
	}
	public void setImportoFinanziamentoRichiesto(Double importoFinanziamentoRichiesto) {
		this.importoFinanziamentoRichiesto = importoFinanziamentoRichiesto;
	}
	
}
