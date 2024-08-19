/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.disimpegni;

import java.io.Serializable;
import java.util.Date;


public class RevocaDTO implements Serializable {
	static final long serialVersionUID = 1;
	private Long idRevoca;
	private Long idProgetto;
	private Long idModalitaAgevolazione;
	private Long idMotivoRevoca;
	private Double importo;
	private String estremi;
	private String note;
	private Date dtRevoca;
	private String descMotivoRevoca;
	private String descModalitaAgevolazione;
	private String descBreveModalitaAgevolaz;
	private String codiceVisualizzato;
	public Long getIdRevoca() {
		return idRevoca;
	}
	public void setIdRevoca(Long idRevoca) {
		this.idRevoca = idRevoca;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	public void setIdModalitaAgevolazione(Long idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	public Long getIdMotivoRevoca() {
		return idMotivoRevoca;
	}
	public void setIdMotivoRevoca(Long idMotivoRevoca) {
		this.idMotivoRevoca = idMotivoRevoca;
	}
	public Double getImporto() {
		return importo;
	}
	public void setImporto(Double importo) {
		this.importo = importo;
	}
	public String getEstremi() {
		return estremi;
	}
	public void setEstremi(String estremi) {
		this.estremi = estremi;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getDtRevoca() {
		return dtRevoca;
	}
	public void setDtRevoca(Date dtRevoca) {
		this.dtRevoca = dtRevoca;
	}
	public String getDescMotivoRevoca() {
		return descMotivoRevoca;
	}
	public void setDescMotivoRevoca(String descMotivoRevoca) {
		this.descMotivoRevoca = descMotivoRevoca;
	}
	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}
	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}
	public String getDescBreveModalitaAgevolaz() {
		return descBreveModalitaAgevolaz;
	}
	public void setDescBreveModalitaAgevolaz(String descBreveModalitaAgevolaz) {
		this.descBreveModalitaAgevolaz = descBreveModalitaAgevolaz;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}

	
	
}
