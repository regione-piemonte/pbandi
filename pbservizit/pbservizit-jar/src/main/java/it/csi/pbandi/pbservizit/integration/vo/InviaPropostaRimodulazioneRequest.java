/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.vo;

import java.util.ArrayList;

import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ModalitaAgevolazione;

public class InviaPropostaRimodulazioneRequest {

	private Long idProgetto;
	private Long idContoEconomico;
	private Long idSoggettoBeneficiario;
	private ArrayList<ModalitaAgevolazione> listaModalitaAgevolazione;
	private String note;
	private Long idRapprensentanteLegale;
	private Long idDelegato;
	private Double importoFinanziamentoRichiesto;
	private TipoAllegatoDTO[] tipiAllegato;

	public Long getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}

	public void setIdSoggettoBeneficiario(Long idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}

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

	public Long getIdDelegato() {
		return idDelegato;
	}

	public void setIdDelegato(Long idDelegato) {
		this.idDelegato = idDelegato;
	}

	public Double getImportoFinanziamentoRichiesto() {
		return importoFinanziamentoRichiesto;
	}

	public void setImportoFinanziamentoRichiesto(Double importoFinanziamentoRichiesto) {
		this.importoFinanziamentoRichiesto = importoFinanziamentoRichiesto;
	}

	public TipoAllegatoDTO[] getTipiAllegato() {
		return tipiAllegato;
	}

	public void setTipiAllegato(TipoAllegatoDTO[] tipiAllegato) {
		this.tipiAllegato = tipiAllegato;
	}

}
