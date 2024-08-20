/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.affidamenti;


public class AffidamentoCheckListDTO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Long ID_SOPRA_SOGLIA = new Long(1);
	private static final Long ID_SOTTO_SOGLIA = new Long(2);
	private static final Long ID_SOGLIA_ND = new Long(0);
	
	private Long idAffidamentoChecklist;
	private Long idNorma;
	private Long idTipoAffidamento;
	private Long idTipoAppalto;
	private Long idTipologiaAggiudicaz;
	private String sopraSoglia;
	private Long idModelloCd;
	private Long idModelloCl;
	private String rifChecklistAffidamenti;
	

	public Long getIdAffidamentoChecklist() {
		return idAffidamentoChecklist;
	}

	public void setIdAffidamentoChecklist(Long idAffidamentoChecklist) {
		this.idAffidamentoChecklist = idAffidamentoChecklist;
	}

	public Long getIdNorma() {
		return idNorma;
	}

	public void setIdNorma(Long idNorma) {
		this.idNorma = idNorma;
	}

	public Long getIdTipoAffidamento() {
		return idTipoAffidamento;
	}

	public void setIdTipoAffidamento(Long idTipoAffidamento) {
		this.idTipoAffidamento = idTipoAffidamento;
	}

	public Long getIdTipoAppalto() {
		return idTipoAppalto;
	}

	public void setIdTipoAppalto(Long idTipoAppalto) {
		this.idTipoAppalto = idTipoAppalto;
	}

	public Long getIdTipologiaAggiudicaz() {
		return idTipologiaAggiudicaz;
	}

	public void setIdTipologiaAggiudicaz(Long idTipologiaAggiudicaz) {
		this.idTipologiaAggiudicaz = idTipologiaAggiudicaz;
	}

	public String getSopraSoglia() {
		return sopraSoglia;
	}

	public void setSopraSoglia(String sopraSoglia) {
		this.sopraSoglia = sopraSoglia;
	}

	public Long getIdModelloCd() {
		return idModelloCd;
	}

	public void setIdModelloCd(Long idModelloCd) {
		this.idModelloCd = idModelloCd;
	}

	public Long getIdModelloCl() {
		return idModelloCl;
	}

	public void setIdModelloCl(Long idModelloCl) {
		this.idModelloCl = idModelloCl;
	}

	public String getRifChecklistAffidamenti() {
		return rifChecklistAffidamenti;
	}

	public void setRifChecklistAffidamenti(String rifChecklistAffidamenti) {
		this.rifChecklistAffidamenti = rifChecklistAffidamenti;
	}

	public static Long getIdSopraSoglia() {
		return ID_SOPRA_SOGLIA;
	}

	public static Long getIdSottoSoglia() {
		return ID_SOTTO_SOGLIA;
	}

	public static Long getIdSogliaNd() {
		return ID_SOGLIA_ND;
	}
	

}
