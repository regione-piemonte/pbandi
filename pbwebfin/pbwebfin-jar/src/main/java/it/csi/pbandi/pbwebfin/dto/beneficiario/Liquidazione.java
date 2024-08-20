/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebfin.dto.beneficiario;

import java.io.Serializable;

import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.CodiceDescrizioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoDatiProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoLeggiAttoLiquidazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.ModalitaAgevolazioneLiquidazioneDTO;
import it.csi.pbandi.pbwebfin.dto.utils.ResponseCodeMessage;

public class Liquidazione implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	DatiGeneraliDTO datiGeneraliDTO;
	ModalitaAgevolazioneLiquidazioneDTO[]  modalitaAgevolazioneLiquidazioneDTO;
	EsitoDatiProgettoDTO esitoDatiProgettoDTO;
	EsitoLeggiAttoLiquidazioneDTO esitoLeggiAttoLiquidazioneDTO;
	CodiceDescrizioneDTO[] codiceDescrizioneErogazioneDTO;
	it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.CodiceDescrizioneDTO[] codiceDescrizioneDTO;
	private ResponseCodeMessage resp;
	
	public DatiGeneraliDTO getDatiGeneraliDTO() {
		return datiGeneraliDTO;
	}
	public void setDatiGeneraliDTO(DatiGeneraliDTO datiGeneraliDTO) {
		this.datiGeneraliDTO = datiGeneraliDTO;
	}
	public ModalitaAgevolazioneLiquidazioneDTO[] getModalitaAgevolazioneLiquidazioneDTO() {
		return modalitaAgevolazioneLiquidazioneDTO;
	}
	public void setModalitaAgevolazioneLiquidazioneDTO(
			ModalitaAgevolazioneLiquidazioneDTO[] modalitaAgevolazioneLiquidazioneDTO) {
		this.modalitaAgevolazioneLiquidazioneDTO = modalitaAgevolazioneLiquidazioneDTO;
	}
	public EsitoDatiProgettoDTO getEsitoDatiProgettoDTO() {
		return esitoDatiProgettoDTO;
	}
	public void setEsitoDatiProgettoDTO(EsitoDatiProgettoDTO esitoDatiProgettoDTO) {
		this.esitoDatiProgettoDTO = esitoDatiProgettoDTO;
	}
	public EsitoLeggiAttoLiquidazioneDTO getEsitoLeggiAttoLiquidazioneDTO() {
		return esitoLeggiAttoLiquidazioneDTO;
	}
	public void setEsitoLeggiAttoLiquidazioneDTO(EsitoLeggiAttoLiquidazioneDTO esitoLeggiAttoLiquidazioneDTO) {
		this.esitoLeggiAttoLiquidazioneDTO = esitoLeggiAttoLiquidazioneDTO;
	}
	public CodiceDescrizioneDTO[] getCodiceDescrizioneErogazioneDTO() {
		return codiceDescrizioneErogazioneDTO;
	}
	public void setCodiceDescrizioneErogazioneDTO(CodiceDescrizioneDTO[] codiceDescrizioneErogazioneDTO) {
		this.codiceDescrizioneErogazioneDTO = codiceDescrizioneErogazioneDTO;
	}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.CodiceDescrizioneDTO[] getCodiceDescrizioneDTO() {
		return codiceDescrizioneDTO;
	}
	public void setCodiceDescrizioneDTO(
			it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.CodiceDescrizioneDTO[] codiceDescrizioneDTO) {
		this.codiceDescrizioneDTO = codiceDescrizioneDTO;
	}
	public ResponseCodeMessage getResp() {
		return resp;
	}
	public void setResp(ResponseCodeMessage resp) {
		this.resp = resp;
	}
	

}
