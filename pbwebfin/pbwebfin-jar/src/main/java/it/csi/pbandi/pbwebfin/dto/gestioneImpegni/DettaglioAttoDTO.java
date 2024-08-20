/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebfin.dto.gestioneImpegni;

import java.io.Serializable;

import it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio.ImpegnoDTO;
import it.csi.pbandi.pbwebfin.dto.utils.ResponseCodeMessage;

public class DettaglioAttoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ImpegnoDTO impegnoDTO;
	private ResponseCodeMessage responseCodeMessage = new ResponseCodeMessage();
	
	public ImpegnoDTO getImpegnoDTO() {
		return impegnoDTO;
	}
	public void setImpegnoDTO(ImpegnoDTO impegnoDTO) {
		this.impegnoDTO = impegnoDTO;
	}
	public ResponseCodeMessage getResponseCodeMessage() {
		return responseCodeMessage;
	}
	public void setResponseCodeMessage(ResponseCodeMessage responseCodeMessage) {
		this.responseCodeMessage = responseCodeMessage;
	}
	
	
}
