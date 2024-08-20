/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.affidamenti;

import java.io.Serializable;

public class NormativaAffidamentoDTO implements Serializable{
	
	static final long serialVersionUID = 1L;
	private Long idNorma;
  	private String descNorma;
	public Long getIdNorma() {
		return idNorma;
	}
	public void setIdNorma(Long idNorma) {
		this.idNorma = idNorma;
	}
	public String getDescNorma() {
		return descNorma;
	}
	public void setDescNorma(String descNorma) {
		this.descNorma = descNorma;
	}
  	
  	
}
