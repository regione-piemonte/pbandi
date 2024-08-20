/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebfin.dto.beneficiario;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import it.csi.pbandi.pbwebfin.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.BeneficiarioBilancioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.DettaglioBeneficiarioBilancioDTO;

public class Beneficiario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private BeneficiarioBilancioDTO beneficiarioBilancioDTO;
	private DettaglioBeneficiarioBilancioDTO[] DettaglioBeneficiarioBilancioDTO;
	private ResponseCodeMessage responseCodeMessage;
	private Long idAttoLiquidazione;
	
	public BeneficiarioBilancioDTO getBeneficiarioBilancioDTO() {
		return beneficiarioBilancioDTO;
	}
	public void setBeneficiarioBilancioDTO(BeneficiarioBilancioDTO beneficiarioBilancioDTO) {
		this.beneficiarioBilancioDTO = beneficiarioBilancioDTO;
	}
	public DettaglioBeneficiarioBilancioDTO[] getDettaglioBeneficiarioBilancioDTO() {
		return DettaglioBeneficiarioBilancioDTO;
	}
	public void setDettaglioBeneficiarioBilancioDTO(DettaglioBeneficiarioBilancioDTO[] dettaglioBeneficiarioBilancioDTO) {
		DettaglioBeneficiarioBilancioDTO = dettaglioBeneficiarioBilancioDTO;
	}
	public ResponseCodeMessage getResponseCodeMessage() {
		return responseCodeMessage;
	}
	public void setResponseCodeMessage(ResponseCodeMessage responseCodeMessage) {
		this.responseCodeMessage = responseCodeMessage;
	}
	public Long getIdAttoLiquidazione() {
		return idAttoLiquidazione;
	}
	public void setIdAttoLiquidazione(Long idAttoLiquidazione) {
		this.idAttoLiquidazione = idAttoLiquidazione;
	}
	@Override
	public String toString() {
		ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this);
		builder.append("beneficiarioBilancioDTO", beneficiarioBilancioDTO);
		builder.append("DettaglioBeneficiarioBilancioDTO", Arrays.toString(DettaglioBeneficiarioBilancioDTO));
		builder.append("responseCodeMessage", responseCodeMessage);
		builder.append("idAttoLiquidazione", idAttoLiquidazione);
		return builder.toString();
	}
	
}
