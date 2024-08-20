/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.affidamenti;

import java.io.Serializable;

public class TipoAffidamentoDTO implements Serializable{

	static final long serialVersionUID = 1L;

	private Long idTipoAffidamento;
  	
  	private String descTipoAffidamento;

	public Long getIdTipoAffidamento() {
		return idTipoAffidamento;
	}

	public void setIdTipoAffidamento(Long idTipoAffidamento) {
		this.idTipoAffidamento = idTipoAffidamento;
	}

	public String getDescTipoAffidamento() {
		return descTipoAffidamento;
	}

	public void setDescTipoAffidamento(String descTipoAffidamento) {
		this.descTipoAffidamento = descTipoAffidamento;
	}
  	
  	
  	
}
