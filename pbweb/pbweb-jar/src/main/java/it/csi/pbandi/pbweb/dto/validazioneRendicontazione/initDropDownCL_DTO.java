/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.validazioneRendicontazione;

import java.util.ArrayList;
import java.util.List;

public class initDropDownCL_DTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private ArrayList<EsitoDichiarazioneDTO> esiti;
	private ArrayList<AttributoEsitoDTO> attributi;
	private List<IdDescModalitaAgevolazioneDTO> modalitaAgevolazione;
	private List<IdDescCausaleErogDTO> causaliErogazione;

	public ArrayList<EsitoDichiarazioneDTO> getEsiti() {
		return esiti;
	}

	public void setEsiti(ArrayList<EsitoDichiarazioneDTO> esiti) {
		this.esiti = esiti;
	}

	public ArrayList<AttributoEsitoDTO> getAttributi() {
		return attributi;
	}

	public void setAttributi(ArrayList<AttributoEsitoDTO> attributi) {
		this.attributi = attributi;
	}

	public List<IdDescModalitaAgevolazioneDTO> getModalitaAgevolazione() {
		return modalitaAgevolazione;
	}

	public void setModalitaAgevolazione(List<IdDescModalitaAgevolazioneDTO> modalitaAgevolazione) {
		this.modalitaAgevolazione = modalitaAgevolazione;
	}

	public List<IdDescCausaleErogDTO> getCausaliErogazione() {
		return causaliErogazione;
	}

	public void setCausaliErogazione(List<IdDescCausaleErogDTO> causaliErogazione) {
		this.causaliErogazione = causaliErogazione;
	}

}
