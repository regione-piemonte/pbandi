/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebfin.dto.beneficiario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.DatiIntegrativiDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EnteCompetenzaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.SettoreEnteDTO;

public class DatiIntegrativi implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String causaleDiPagamento;
	private Long idEnteCompetenza;
	private Long idSettoreDiAppartenenza;
	private EnteCompetenzaDTO[] listEnteDiCompetenza;
	private ArrayList<SettoreEnteDTO> listSettoreDiAppartenenza;
	private DatiIntegrativiDTO datiIntegrativi;
	
	public String getCausaleDiPagamento() {
		return causaleDiPagamento;
	}
	public void setCausaleDiPagamento(String causaleDiPagamento) {
		this.causaleDiPagamento = causaleDiPagamento;
	}
	public Long getIdEnteCompetenza() {
		return idEnteCompetenza;
	}
	public void setIdEnteCompetenza(Long idEnteCompetenza) {
		this.idEnteCompetenza = idEnteCompetenza;
	}
	public Long getIdSettoreDiAppartenenza() {
		return idSettoreDiAppartenenza;
	}
	public void setIdSettoreDiAppartenenza(Long idSettoreDiAppartenenza) {
		this.idSettoreDiAppartenenza = idSettoreDiAppartenenza;
	}
	public EnteCompetenzaDTO[] getListEnteDiCompetenza() {
		return listEnteDiCompetenza;
	}
	public void setListEnteDiCompetenza(EnteCompetenzaDTO[] listEnteDiCompetenza) {
		this.listEnteDiCompetenza = listEnteDiCompetenza;
	}
	public ArrayList<SettoreEnteDTO> getListSettoreDiAppartenenza() {
		return listSettoreDiAppartenenza;
	}
	public void setListSettoreDiAppartenenza(ArrayList<SettoreEnteDTO> listSettoreDiAppartenenza) {
		this.listSettoreDiAppartenenza = listSettoreDiAppartenenza;
	}
	public DatiIntegrativiDTO getDatiIntegrativi() {
		return datiIntegrativi;
	}
	public void setDatiIntegrativi(DatiIntegrativiDTO datiIntegrativi) {
		this.datiIntegrativi = datiIntegrativi;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DatiIntegrativi [causaleDiPagamento=");
		builder.append(causaleDiPagamento);
		builder.append(", idEnteCompetenza=");
		builder.append(idEnteCompetenza);
		builder.append(", idSettoreDiAppartenenza=");
		builder.append(idSettoreDiAppartenenza);
		builder.append(", listEnteDiCompetenza=");
		builder.append(Arrays.toString(listEnteDiCompetenza));
		builder.append(", listSettoreDiAppartenenza=");
		builder.append(listSettoreDiAppartenenza);
		builder.append(", datiIntegrativi=");
		builder.append(datiIntegrativi);
		builder.append("]");
		return builder.toString();
	}
	
}
