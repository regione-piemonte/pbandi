/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

public class FiltroProcedureAggiudicazione implements java.io.Serializable {

	private Long idTipologiaAggiudicaz;

	public void setIdTipologiaAggiudicaz(Long val) {
		idTipologiaAggiudicaz = val;
	}

	public Long getIdTipologiaAggiudicaz() {
		return idTipologiaAggiudicaz;
	}

	private String codProcAgg;

	public void setCodProcAgg(String val) {
		codProcAgg = val;
	}

	public String getCodProcAgg() {
		return codProcAgg;
	}

	private String cigProcAgg;

	public void setCigProcAgg(String val) {
		cigProcAgg = val;
	}

	public String getCigProcAgg() {
		return cigProcAgg;
	}

	private static final long serialVersionUID = 1L;

	public FiltroProcedureAggiudicazione() {
		super();

	}

	public String toString() {
		return super.toString();
	}

}
