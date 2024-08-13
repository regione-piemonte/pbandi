/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.request;

import it.csi.pbandi.pbweb.dto.QualificaFormDTO;

public class SalvaQualificaRequest {
	
	private QualificaFormDTO qualificaFormDTO;
	
	private Long idUtente;

	public QualificaFormDTO getQualificaFormDTO() {
		return qualificaFormDTO;
	}

	public void setQualificaFormDTO(QualificaFormDTO qualificaFormDTO) {
		this.qualificaFormDTO = qualificaFormDTO;
	}

	public Long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}

}
