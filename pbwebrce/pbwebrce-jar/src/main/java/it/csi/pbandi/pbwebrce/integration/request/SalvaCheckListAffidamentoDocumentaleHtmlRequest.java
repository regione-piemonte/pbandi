/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.request;

import it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.ChecklistAffidamentoHtmlDTO;


public class SalvaCheckListAffidamentoDocumentaleHtmlRequest {

	
	private Long idProgetto;
	private String codStatoTipoDocIndex;
	private ChecklistAffidamentoHtmlDTO checklistHtmlDTO;
    private Long idAffidamento;
	private Boolean isRichiestaIntegrazione;
	private String noteRichiestaIntegrazione;
	private String codTipoChecklist;
	
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getCodStatoTipoDocIndex() {
		return codStatoTipoDocIndex;
	}
	public void setCodStatoTipoDocIndex(String codStatoTipoDocIndex) {
		this.codStatoTipoDocIndex = codStatoTipoDocIndex;
	}
	public ChecklistAffidamentoHtmlDTO getChecklistHtmlDTO() {
		return checklistHtmlDTO;
	}
	public void setChecklistHtmlDTO(ChecklistAffidamentoHtmlDTO checklistHtmlDTO) {
		this.checklistHtmlDTO = checklistHtmlDTO;
	}
	public Long getIdAffidamento() {
		return idAffidamento;
	}
	public void setIdAffidamento(Long idAffidamento) {
		this.idAffidamento = idAffidamento;
	}
	public Boolean getIsRichiestaIntegrazione() {
		return isRichiestaIntegrazione;
	}
	public void setIsRichiestaIntegrazione(Boolean isRichiestaIntegrazione) {
		this.isRichiestaIntegrazione = isRichiestaIntegrazione;
	}
	public String getNoteRichiestaIntegrazione() {
		return noteRichiestaIntegrazione;
	}
	public void setNoteRichiestaIntegrazione(String noteRichiestaIntegrazione) {
		this.noteRichiestaIntegrazione = noteRichiestaIntegrazione;
	}
	public String getCodTipoChecklist() {
		return codTipoChecklist;
	}
	public void setCodTipoChecklist(String codTipoChecklist) {
		this.codTipoChecklist = codTipoChecklist;
	}
	@Override
	public String toString() {
		return "SalvaCheckListAffidamentoDocumentaleHtmlRequest [idProgetto=" + idProgetto + ", codStatoTipoDocIndex="
				+ codStatoTipoDocIndex + ", checklistHtmlDTO=" + checklistHtmlDTO + ", idAffidamento=" + idAffidamento
				+ ", isRichiestaIntegrazione=" + isRichiestaIntegrazione + ", noteRichiestaIntegrazione="
				+ noteRichiestaIntegrazione + ", codTipoChecklist=" + codTipoChecklist + "]";
	}
    

}
