/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ConsuntivoEntrataDTO implements java.io.Serializable {

  static final long serialVersionUID = 2L;

	private BigDecimal idConsuntivoEntrata;
	private BigDecimal importoEntrata;
	private BigDecimal idVoceDiEntrata;
	private BigDecimal idRigoContoEconomico;
	private String completamento;
	private BigDecimal idUtenteIns;
	private BigDecimal idUtenteAgg;
	private Date dtInizioValidita;
	private Date dtFineValidita;
	private Date dtModifica;

	//Left Join con PBANDI_D_VOCE_DI_ENTRATA
	private String descrizione;
	private String flagEdit;

	public ConsuntivoEntrataDTO() {
	}

	public ConsuntivoEntrataDTO(BigDecimal idConsuntivoEntrata) {

		this.idConsuntivoEntrata = idConsuntivoEntrata;
	}

	public ConsuntivoEntrataDTO(BigDecimal idConsuntivoEntrata, BigDecimal importoEntrata, BigDecimal idVoceDiEntrata, BigDecimal idRigoContoEconomico, String completamento, BigDecimal idUtenteIns, BigDecimal idUtenteAgg, Date dtInizioValidita, Date dtFineValidita, Date dtModifica) {
		this.idConsuntivoEntrata = idConsuntivoEntrata;
		this.importoEntrata = importoEntrata;
		this.idVoceDiEntrata = idVoceDiEntrata;
		this.idRigoContoEconomico = idRigoContoEconomico;
		this.completamento = completamento;
		this.idUtenteIns = idUtenteIns;
		this.idUtenteAgg = idUtenteAgg;
		this.dtInizioValidita = dtInizioValidita;
		this.dtFineValidita = dtFineValidita;
		this.dtModifica = dtModifica;
	}

	public ConsuntivoEntrataDTO(long idConsuntivoEntrata) {
		this.idConsuntivoEntrata = BigDecimal.valueOf(idConsuntivoEntrata);
	}

	public ConsuntivoEntrataDTO(BigDecimal idConsuntivoEntrata, BigDecimal importoEntrata, BigDecimal idVoceDiEntrata, BigDecimal idRigoContoEconomico, String completamento, BigDecimal idUtenteIns, BigDecimal idUtenteAgg, Date dtInizioValidita, Date dtFineValidita, Date dtModifica, String descrizione, String flagEdit) {
		this.idConsuntivoEntrata = idConsuntivoEntrata;
		this.importoEntrata = importoEntrata;
		this.idVoceDiEntrata = idVoceDiEntrata;
		this.idRigoContoEconomico = idRigoContoEconomico;
		this.completamento = completamento;
		this.idUtenteIns = idUtenteIns;
		this.idUtenteAgg = idUtenteAgg;
		this.dtInizioValidita = dtInizioValidita;
		this.dtFineValidita = dtFineValidita;
		this.dtModifica = dtModifica;
		this.descrizione = descrizione;
		this.flagEdit = flagEdit;
	}

	public BigDecimal getIdConsuntivoEntrata() {
		return idConsuntivoEntrata;
	}

	public void setIdConsuntivoEntrata(BigDecimal idConsuntivoEntrata) {
		this.idConsuntivoEntrata = idConsuntivoEntrata;
	}

	public BigDecimal getImportoEntrata() {
		return importoEntrata;
	}

	public void setImportoEntrata(BigDecimal importoEntrata) {
		this.importoEntrata = importoEntrata;
	}

	public BigDecimal getIdVoceDiEntrata() {
		return idVoceDiEntrata;
	}

	public void setIdVoceDiEntrata(BigDecimal idVoceDiEntrata) {
		this.idVoceDiEntrata = idVoceDiEntrata;
	}

	public BigDecimal getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}

	public void setIdRigoContoEconomico(BigDecimal idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}

	public String getCompletamento() {
		return completamento;
	}

	public void setCompletamento(String completamento) {
		this.completamento = completamento;
	}

	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}

	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}

	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}

	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}

	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public Date getDtModifica() {
		return dtModifica;
	}

	public void setDtModifica(Date dtModifica) {
		this.dtModifica = dtModifica;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getFlagEdit() {
		return flagEdit;
	}

	public void setFlagEdit(String flagEdit) {
		this.flagEdit = flagEdit;
	}

	@Override
	public String toString() {
		return "ConsuntivoEntrataDTO{" +
				"idConsuntivoEntrata=" + idConsuntivoEntrata +
				", importoEntrata=" + importoEntrata +
				", idVoceDiEntrata=" + idVoceDiEntrata +
				", idRigoContoEconomico=" + idRigoContoEconomico +
				", completamento='" + completamento + '\'' +
				", idUtenteIns=" + idUtenteIns +
				", idUtenteAgg=" + idUtenteAgg +
				", dtInizioValidita=" + dtInizioValidita +
				", dtFineValidita=" + dtFineValidita +
				", dtModifica=" + dtModifica +
				", descrizione='" + descrizione + '\'' +
				", flagEdit='" + flagEdit + '\'' +
				'}';
	}
}