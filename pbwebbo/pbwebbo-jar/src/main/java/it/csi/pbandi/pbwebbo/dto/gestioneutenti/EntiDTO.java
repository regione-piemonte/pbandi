/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.gestioneutenti;

import java.io.Serializable;
import java.util.List;

import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.IdDescBreveDescEstesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica;

public class EntiDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<Decodifica> entiAssociabiliFiltrati;
	private List<IdDescBreveDescEstesaDTO> entiAssociatiList;
	
	public List<Decodifica> getEntiAssociabiliFiltrati() {
		return entiAssociabiliFiltrati;
	}
	public void setEntiAssociabiliFiltrati(List<Decodifica> entiAssociabiliFiltrati) {
		this.entiAssociabiliFiltrati = entiAssociabiliFiltrati;
	}
	public List<IdDescBreveDescEstesaDTO> getEntiAssociatiList() {
		return entiAssociatiList;
	}
	public void setEntiAssociatiList(List<IdDescBreveDescEstesaDTO> entiAssociatiList) {
		this.entiAssociatiList = entiAssociatiList;
	}

}
