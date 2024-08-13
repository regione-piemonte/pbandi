/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.configurazionebandolinea;

import java.io.Serializable;
import java.util.Arrays;

public class CheclistDTO implements Serializable {
	
	private static final long serialVersionUID = -7755136316666916087L;
	
	private Long[] idTipoDocumentoIndexArray;
	private Long[] idModelloArray;
	private Long idU;
	private Long progrBandoLineaIntervento;
	
	
	public Long[] getIdTipoDocumentoIndexArray() {
		return idTipoDocumentoIndexArray;
	}
	public void setIdTipoDocumentoIndexArray(Long[] idTipoDocumentoIndexArray) {
		this.idTipoDocumentoIndexArray = idTipoDocumentoIndexArray;
	}
	public Long[] getIdModelloArray() {
		return idModelloArray;
	}
	public void setIdModelloArray(Long[] idModelloArray) {
		this.idModelloArray = idModelloArray;
	}
	
	public Long getIdU() {
		return idU;
	}
	public void setIdU(Long idU) {
		this.idU = idU;
	}
	
	public Long getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(Long progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	
	@Override
	public String toString() {
		return "CheclistDTO [idTipoDocumentoIndexArray=" + Arrays.toString(idTipoDocumentoIndexArray)
				+ ", idModelloArray=" + Arrays.toString(idModelloArray) + ", idU=" + idU
				+ ", progrBandoLineaIntervento=" + progrBandoLineaIntervento + "]";
	}
	
}
