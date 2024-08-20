/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebfin.dto.beneficiario;

import java.io.Serializable;
import java.util.Arrays;

import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.ComuneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.CoordinateBancarieDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.ProvinciaDTO;

public class DettaglioBeneficiario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Decodifica[] modalitaErogazione;
	private Decodifica[] nazioni;
	private ProvinciaDTO[] province;
	private ComuneDTO[] comuneDTO;
	private CoordinateBancarieDTO coordinateBancarieDTO;
	
	public Decodifica[] getModalitaErogazione() {
		return modalitaErogazione;
	}
	public void setModalitaErogazione(Decodifica[] modalitaErogazione) {
		this.modalitaErogazione = modalitaErogazione;
	}
	public Decodifica[] getNazioni() {
		return nazioni;
	}
	public void setNazioni(Decodifica[] nazioni) {
		this.nazioni = nazioni;
	}
	public ProvinciaDTO[] getProvince() {
		return province;
	}
	public void setProvince(ProvinciaDTO[] province) {
		this.province = province;
	}
	public ComuneDTO[] getComuneDTO() {
		return comuneDTO;
	}
	public void setComuneDTO(ComuneDTO[] comuneDTO) {
		this.comuneDTO = comuneDTO;
	}
	public CoordinateBancarieDTO getCoordinateBancarieDTO() {
		return coordinateBancarieDTO;
	}
	public void setCoordinateBancarieDTO(CoordinateBancarieDTO coordinateBancarieDTO) {
		this.coordinateBancarieDTO = coordinateBancarieDTO;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DettaglioBeneficiario [modalitaErogazione=");
		builder.append(Arrays.toString(modalitaErogazione));
		builder.append(", nazioni=");
		builder.append(Arrays.toString(nazioni));
		builder.append(", province=");
		builder.append(Arrays.toString(province));
		builder.append(", comuneDTO=");
		builder.append(Arrays.toString(comuneDTO));
		builder.append(", coordinateBancarieDTO=");
		builder.append(coordinateBancarieDTO);
		builder.append("]");
		return builder.toString();
	}

}
