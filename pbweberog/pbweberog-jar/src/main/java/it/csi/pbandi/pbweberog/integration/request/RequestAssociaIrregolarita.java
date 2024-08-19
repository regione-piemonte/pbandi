/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.SaveDettaglioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.SaveDsDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.SaveIrregolaritaDTO;

public class RequestAssociaIrregolarita {
	private SaveIrregolaritaDTO[] elencoSaveIrregolaritaDTO; 
	private SaveDettaglioDTO[] elencoSaveDettaglioDTO;
	private SaveDsDTO[] elencoSaveDsDTO;
	private Long idRevoca ;
	public SaveIrregolaritaDTO[] getElencoSaveIrregolaritaDTO() {
		return elencoSaveIrregolaritaDTO;
	}
	public void setElencoSaveIrregolaritaDTO(SaveIrregolaritaDTO[] elencoSaveIrregolaritaDTO) {
		this.elencoSaveIrregolaritaDTO = elencoSaveIrregolaritaDTO;
	}
	public SaveDettaglioDTO[] getElencoSaveDettaglioDTO() {
		return elencoSaveDettaglioDTO;
	}
	public void setElencoSaveDettaglioDTO(SaveDettaglioDTO[] elencoSaveDettaglioDTO) {
		this.elencoSaveDettaglioDTO = elencoSaveDettaglioDTO;
	}
	public SaveDsDTO[] getElencoSaveDsDTO() {
		return elencoSaveDsDTO;
	}
	public void setElencoSaveDsDTO(SaveDsDTO[] elencoSaveDsDTO) {
		this.elencoSaveDsDTO = elencoSaveDsDTO;
	}
	public Long getIdRevoca() {
		return idRevoca;
	}
	public void setIdRevoca(Long idRevoca) {
		this.idRevoca = idRevoca;
	}
	
	

}
