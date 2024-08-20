/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoAssociaFilesDTO;

public class ResponseCodeMessageAllegati extends ResponseCodeMessage {

	private static final long serialVersionUID = 1L;

	private EsitoAssociaFilesDTO esitoAssociaFilesDTO;

	public ResponseCodeMessageAllegati(String code, String message, EsitoAssociaFilesDTO esitoAssociaFilesDTO) {
		super(code, message);
		this.esitoAssociaFilesDTO = esitoAssociaFilesDTO;
	}

	public EsitoAssociaFilesDTO getEsitoAssociaFilesDTO() {
		return esitoAssociaFilesDTO;
	}

	public void setEsitoAssociaFilesDTO(EsitoAssociaFilesDTO esitoAssociaFilesDTO) {
		this.esitoAssociaFilesDTO = esitoAssociaFilesDTO;
	}

}
