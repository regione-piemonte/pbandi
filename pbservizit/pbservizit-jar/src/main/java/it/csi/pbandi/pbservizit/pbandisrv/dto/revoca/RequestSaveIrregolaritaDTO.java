/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.revoca;

public class RequestSaveIrregolaritaDTO implements java.io.Serializable {
	
	static final long serialVersionUID = 1;
	private SaveIrregolaritaDTO[] elencoSaveIrregolaritaDTO;
	private SaveDettaglioDTO[] elencoSaveDettaglioDTO;
	private SaveDsDTO[] elencoSaveDsDTO;
	private Long idRevoca;
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
