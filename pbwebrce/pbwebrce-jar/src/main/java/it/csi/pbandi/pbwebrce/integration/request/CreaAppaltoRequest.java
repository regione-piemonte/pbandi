/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.request;

import it.csi.pbandi.pbwebrce.dto.affidamenti.Appalto;

public class CreaAppaltoRequest {
	private Appalto appalto;
	private Long idProceduraAggiudicaz;
	public Appalto getAppalto() {
		return appalto;
	}
	public void setAppalto(Appalto appalto) {
		this.appalto = appalto;
	}
	public Long getIdProceduraAggiudicaz() {
		return idProceduraAggiudicaz;
	}
	public void setIdProceduraAggiudicaz(Long idProceduraAggiudicaz) {
		this.idProceduraAggiudicaz = idProceduraAggiudicaz;
	}
	
	
	
	
}
